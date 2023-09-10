package com.euparliament.broadcast;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.euparliament.broadcast.exception.NotFoundException;
import com.euparliament.broadcast.model.ConsensusReferendum;
import com.euparliament.broadcast.model.Referendum;
import com.euparliament.broadcast.model.ReferendumMessage;
import com.euparliament.broadcast.model.ResourceMapping;
import com.euparliament.broadcast.utils.HttpRequest;


@Component
public class Receiver {
	
	final RabbitTemplate rabbitTemplate;
	RestTemplate restTemplate;
	
	@Autowired
	ResourceMapping resourceMapping;
	
	public Receiver(RabbitTemplate rabbitTemplate) {
		restTemplate = new RestTemplate();
		this.rabbitTemplate = rabbitTemplate;
	}
 	
	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {

		try {
			Referendum referendum = Referendum.toReferendum(message);
			// store the referendum proposal
    		HttpEntity<Referendum> referendumEntity = new HttpEntity<Referendum>(referendum);
    		String response1 = restTemplate.postForObject(resourceMapping.getUrlReferendum(), referendumEntity, String.class);
    		System.out.println("Referendum created: " + response1);
    		
    		// post consensus data structures
    		ConsensusReferendum consensusReferendum = new ConsensusReferendum(
    				referendum.getId().getTitle(),
    				referendum.getId().getDateStartConsensusProposal(),
    				2 // first consensus
    		);
    		HttpEntity<ConsensusReferendum> consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);
    		String response2 = restTemplate.postForObject(resourceMapping.getUrlConsensusReferendum(), consensusReferendumEntity, String.class);
    		System.out.println("ConsensusReferendum created: " + response2);

		} catch (Exception e) {
			
			ReferendumMessage referendumMessage = ReferendumMessage.toReferendumMessage(message);
			System.out.println("ReferendumMessage received: " + referendumMessage.toString());
			
			// get consensus data structure from the database
			ConsensusReferendum consensusReferendum;
			try {
				consensusReferendum = HttpRequest.getConsensusReferendum(
						referendumMessage.getTitle(),
						referendumMessage.getDateStartConsensusProposal(),
						this.resourceMapping);
			} catch (NotFoundException nFE) {
	    		System.out.println("Error: referendum not already received");
				return;
			}
			
			// check if the status of the message is coherent with the status of the referendum, to avoid obsolete messages
			if(consensusReferendum.getStatus() != referendumMessage.getStatus()) {
	    		System.out.println("Warning: recived message status is different from the current status for the referendum: " + 
	    				referendumMessage.getTitle() + ", " +
	    				referendumMessage.getDateStartConsensusProposal()
	    				);
				return;
			}
			
			// check the type of the message
			if(referendumMessage.getIsDecision() &&
			   consensusReferendum.isCorrect(referendumMessage.getNationSourceAnswer()) &&
			   consensusReferendum.getDecision()
			) {
				this.computeDecision(referendumMessage.getAnswer(), referendumMessage);
				return;
			}
			// compute the proposal

            // update consensusReferendum values
			consensusReferendum.updateProposals(
					referendumMessage.getProposals(), 
					referendumMessage.getRound()
			);
			consensusReferendum.updateRecivedFrom(
					referendumMessage.getNationSourceAnswer(), 
					referendumMessage.getRound()
			);

    		// put new values in the database
			HttpRequest.putConsensusReferendum(consensusReferendum, resourceMapping);
		    System.out.println("Updating consensus data structures: " + consensusReferendum);
		    
		    // check decision conditions
		    if(consensusReferendum.checkCorrectSubsetOfReceiveFrom() &&
		       consensusReferendum.getDecision() == null) {
		    	if(consensusReferendum.checkReceivedFromNotChanged()
		    	) {
		    		// take decision
		    		Boolean decision = consensusReferendum.decide();
		    		System.out.println("Decision taken: " + decision);
		    		
		    		this.computeDecision(decision, referendumMessage);				    		
		    		
		    	} else {
		    		consensusReferendum.incrementRound();
		    		// update ConsensusReferendum
		    	}
		    }
		    
    		latch.countDown();
		}

	}

	private void computeDecision(Boolean decision, ReferendumMessage referendumMessage) {
		// send decision message to broadcast
		ReferendumMessage decisionReferendumMessage = new ReferendumMessage(
			referendumMessage.getTitle(),
			referendumMessage.getStatus(),
			resourceMapping.getQueueName(),
			decision,
			null,
			null,
			true,
			referendumMessage.getDateStartConsensusProposal()
		);
		rabbitTemplate.convertAndSend(
				BroadcastApplication.topicExchangeName, 
				"foo.bar.baz", 
				decisionReferendumMessage.toString());

		
		// delete ConsensusReferendum if the referendum is aborted, else clean it
		if((decision != true && referendumMessage.getStatus() == 2) ||
			referendumMessage.getStatus() == 4) {
			// delete ConsensusReferendum
			HttpRequest.deleteConsensusReferendum(
					referendumMessage.getTitle(),
					referendumMessage.getDateStartConsensusProposal(),
					this.resourceMapping);
		} else {
			// clean
			ConsensusReferendum newConsensusReferendum = new ConsensusReferendum(
					referendumMessage.getTitle(),
					referendumMessage.getDateStartConsensusProposal(),
					4
			);
			HttpRequest.putConsensusReferendum(newConsensusReferendum, resourceMapping);
		}
		
		// update Referendum
		Referendum referendum = HttpRequest.getReferendum(
				referendumMessage.getTitle(), 
				referendumMessage.getDateStartConsensusProposal(), 
				resourceMapping
		);
		referendum.setStatusByProposalConsensusDecision(decision);
		HttpRequest.putReferendum(referendum, resourceMapping);
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
