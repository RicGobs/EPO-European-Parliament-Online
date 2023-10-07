package com.euparliament.broadcast;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.euparliament.broadcast.exception.NotFoundException;
import com.euparliament.broadcast.model.ConsensusReferendum;
import com.euparliament.broadcast.model.Referendum;
import com.euparliament.broadcast.model.ReferendumMessage;
import com.euparliament.broadcast.model.ResourceMapping;
import com.euparliament.broadcast.utils.HttpRequest;

@Component
@RestController
public class Sender {

	final RabbitTemplate rabbitTemplate;
	
	@Autowired
	ResourceMapping resourceMapping;

	public Sender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	//for status 1 - messages for knowing the proposals
	@CrossOrigin()
	@PostMapping("/europeanReferendumProposal")
	public String sendEuropeanReferendumProposal(@RequestBody Referendum referendum) {
		System.out.println("\nSending an European Referendum to all. Title: " + referendum.getId().getTitle() + ", Argument: " + referendum.getArgument() + "\n");
		referendum.setNationCreator(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendum.toString());
		return "Message sent: " + referendum.toString();
	}

	//for status 2 - propose value for first consensus
	@CrossOrigin()
	@PostMapping("/europeanReferendumFirstConsensus")
	public String referendum(@RequestBody ReferendumMessage referendumMessage) {
		System.out.println("\nSending the proposal answer: " + referendumMessage.getAnswer() + " to all, for the following Referendum:");
		System.out.println("Title: " + referendumMessage.getTitle() + ", Creation date: " + referendumMessage.getDateStartConsensusProposal() + "\n");
		// check if referendum.status = 1 (proposal answer not already sent)
		Referendum referendum;
		try {
			referendum = HttpRequest.getReferendum(
					referendumMessage.getTitle(),
					referendumMessage.getDateStartConsensusProposal(),
					this.resourceMapping);
		} catch (NotFoundException e) {
			return "Referendum not found";
		}
		
		if(referendum.getStatus() > 1) {
			System.out.println("Answer to proposal already sent, status = " + referendum.getStatus());
			throw new ResponseStatusException(
					  HttpStatus.BAD_REQUEST, "Referendum answer already sent"
					);
		}
		// update status = 2
		referendum.setStatus(2);
		HttpRequest.putReferendum(referendum, resourceMapping);
		
		// get consensus data structure from the database
		ConsensusReferendum consensusReferendum;
		try {
			consensusReferendum = HttpRequest.getConsensusReferendum(
					referendumMessage.getTitle(),
					referendumMessage.getDateStartConsensusProposal(),
					this.resourceMapping);
		} catch (NotFoundException e) {
			return "Internal error: cannot find ConsensusReferendum for this Referendum";
		}
		
		// append this vote to the proposals
		consensusReferendum.addProposalToRound(referendumMessage.getAnswer(), 1, this.resourceMapping.getQueueName());
		referendumMessage.setProposals(consensusReferendum.getProposals());

		// put the updates to the database
		HttpRequest.putConsensusReferendum(consensusReferendum, resourceMapping);
		
		// send the propose to broadcast
		referendumMessage.setIsDecision(false);
		referendumMessage.setRound(1);
		referendumMessage.setStatus(2);
		referendumMessage.setNationSourceAnswer(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendumMessage.toString());
		return "Message sent: " + referendumMessage.toString();
	}
}