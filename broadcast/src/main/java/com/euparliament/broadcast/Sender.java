package com.euparliament.broadcast;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	@PostMapping("/europeanReferendumProposal")
	public String sendEuropeanReferendumProposal(@RequestBody Referendum referendum) {
		referendum.setNationCreator(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendum.toString());
		return "Message sent: " + referendum.toString();
	}

	//for status 2 - propose value for first consensus
	@PostMapping("/europeanReferendumFirstConsensus")
	public String referendum(@RequestBody ReferendumMessage referendumMessage) {
		System.out.println("referendumMessage : " + referendumMessage.toString());
		// check if referendum.status = 1 (proposal answer not already sent)
		Referendum referendum = HttpRequest.getReferendum(
				referendumMessage.getTitle(),
				referendumMessage.getDateStartConsensusProposal(),
				this.resourceMapping);
		System.out.println("referendum : " + referendum.toString());
		
		if(referendum.getStatus() > 1) {
			throw new ResponseStatusException(
					  HttpStatus.BAD_REQUEST, "Referendum answer already sent"
					);
		}
		// update status = 2
		referendum.setStatus(2);
		HttpEntity<Referendum> referendumEntity = new HttpEntity<Referendum>(referendum);
		resourceMapping.getRestTemplate().exchange(
				resourceMapping.getUrlReferendum(), 
				HttpMethod.PUT, 
				referendumEntity, 
				String.class);
		
		// get consensus data structure from the database
		ConsensusReferendum consensusReferendum = HttpRequest.getConsensusReferendum(
				referendumMessage.getTitle(),
				referendumMessage.getDateStartConsensusProposal(),
				this.resourceMapping);
		System.out.println("consensusReferendum : " + consensusReferendum.toString());
		
		// append this vote to the proposals
		consensusReferendum.addProposalToRound(referendumMessage.getAnswer(), 1);
		HttpEntity<ConsensusReferendum> consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);

		// put the updates to the database
		resourceMapping.getRestTemplate().exchange(
				resourceMapping.getUrlConsensusReferendum(), 
				HttpMethod.PUT, 
				consensusReferendumEntity, 
				String.class);
		
		// send the propose to broadcast
		referendumMessage.setStatus(2);
		referendumMessage.setNationSourceAnswer(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendumMessage.toString());
		return "Message sent: " + referendumMessage.toString();
	}

	//for status 3 - messages for knowing that a Nation is doing the Referendum (debug purpose)
	@PostMapping("/europeanReferendumDoing")
	public String sendEuropeanReferendumDoing(@RequestBody ReferendumMessage referendumMessage) {
		referendumMessage.setStatus(3);
		referendumMessage.setNationSourceAnswer(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendumMessage.toString());
		return "Message sent: " + referendumMessage.toString();
	}

	//for status 4 - messages for second consensus
	@PostMapping("/europeanReferendumSecondConsensus")
	public String sendEuropeanReferendumResult(@RequestBody ReferendumMessage referendumMessage) {
		referendumMessage.setStatus(4);
		referendumMessage.setNationSourceAnswer(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendumMessage.toString());
		return "Message sent: " + referendumMessage.toString();
	}
}