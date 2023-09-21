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
import com.euparliament.broadcast.utils.CheckTime;
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
  			referendum.setPopulation(this.resourceMapping.getPopulation());
  			
			System.out.println("\nRecived a new European Referendum by the Nation " + referendum.getNationCreator());
			System.out.println("Title: " + referendum.getId().getTitle() + ", Argument: " + referendum.getArgument());
			System.out.println("Please answer to this referendum by: " + referendum.getDateEndConsensusProposal() + "\n");

			CheckTime myThread = new CheckTime(
					referendum.getId().getTitle(), 
					referendum.getId().getDateStartConsensusProposal(), 
					referendum.getDateEndConsensusProposal(),1,this);
  			myThread.start();

			myThread = new CheckTime(
					referendum.getId().getTitle(), 
					referendum.getId().getDateStartConsensusProposal(), 
					referendum.getDateEndResult(),2,this);
  			myThread.start();

			myThread = new CheckTime(
					referendum.getId().getTitle(), 
					referendum.getId().getDateStartConsensusProposal(), 
					referendum.getDateEndConsensusResult(),3,this);
  			myThread.start();

			// store the referendum proposal
    		HttpEntity<Referendum> referendumEntity = new HttpEntity<Referendum>(referendum);
    		restTemplate.postForObject(resourceMapping.getUrlReferendum(), referendumEntity, String.class);
    		
    		// post consensus data structures
    		ConsensusReferendum consensusReferendum = new ConsensusReferendum(
    				referendum.getId().getTitle(),
    				referendum.getId().getDateStartConsensusProposal(),
    				2, // first consensus
    				this.resourceMapping.getListNations()
    		);
    		HttpEntity<ConsensusReferendum> consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);
    		restTemplate.postForObject(resourceMapping.getUrlConsensusReferendum(), consensusReferendumEntity, String.class);

		} catch (Exception e) {
			
			ReferendumMessage referendumMessage = ReferendumMessage.toReferendumMessage(message);
			System.out.println("\nRecived a consensus message by the Nation " + referendumMessage.getNationSourceAnswer());
			System.out.println("Title: " + referendumMessage.getTitle() + ", Creation Date: " + referendumMessage.getDateStartConsensusProposal());
			System.out.println(referendumMessage.printMessage());
			
			// get consensus data structure from the database
			ConsensusReferendum consensusReferendum;
			Referendum referendum;
			try {
				referendum = HttpRequest.getReferendum(
						referendumMessage.getTitle(), 
						referendumMessage.getDateStartConsensusProposal(), 
						resourceMapping
				);
			} catch (NotFoundException nFE) {
	    		System.out.println("Error: referendum not already received\n");
	    		return;
			}
			try {
				consensusReferendum = HttpRequest.getConsensusReferendum(
						referendumMessage.getTitle(),
						referendumMessage.getDateStartConsensusProposal(),
						this.resourceMapping);
			} catch (NotFoundException nFE) {
				System.out.println("Discarding message, decision is already taken\n");
				return;
			}
			
			// check if the status of the message is coherent with the status of the referendum, to avoid obsolete messages
			if(consensusReferendum.getStatus() != referendumMessage.getStatus()) {
	    		System.out.println("Discarding message: recived message status is different from the current status for the referendum: " + 
	    				referendumMessage.getTitle() + ", " +
	    				referendumMessage.getDateStartConsensusProposal() + "\n"
	    				);
				return;
			}
			
			// check the type of the message
			if(referendumMessage.getIsDecision() &&
			   consensusReferendum.isCorrect(referendumMessage.getNationSourceAnswer())
			) {
				this.computeDecision(referendumMessage.getAnswer(), referendumMessage.getStatus(), referendum);
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
			System.out.println("Local consensus data structurues. Correct: " + consensusReferendum.getCorrect() + ", Round: " + consensusReferendum.getRound() + ", Proposals: " + consensusReferendum.getProposals() + ", ReceivedFrom: " + consensusReferendum.getReceivedFrom()+ "\n");
		    
		    // check decision condition. We already know that the condition is not already taken
		    if(consensusReferendum.checkCorrectSubsetOfReceiveFrom()) {
		    	if(consensusReferendum.checkReceivedFromNotChanged()
		    	) {
		    		// take decision
		    		Boolean decision = consensusReferendum.decide();
		    		System.out.println("Decision taken: " + decision + "\n");
		    		
		    		this.computeDecision(decision, referendumMessage.getStatus(), referendum);				    		
		    		
		    	} else {
		    		consensusReferendum.incrementRound();
		    		// update ConsensusReferendum
		    	}
		    }
		   
		}
		
		latch.countDown();

	}

	public void computeDecision(Boolean decision, Integer statusMessage, Referendum referendum) {
		// send decision message to broadcast
		System.out.println("Sending the proposal decision: " + decision + " to all.\n");
		ReferendumMessage decisionReferendumMessage = new ReferendumMessage(
			referendum.getId().getTitle(),
			statusMessage,
			resourceMapping.getQueueName(),
			decision,
			null,
			null,
			true,
			referendum.getId().getDateStartConsensusProposal()
		);
		rabbitTemplate.convertAndSend(
				BroadcastApplication.topicExchangeName, 
				"foo.bar.baz", 
				decisionReferendumMessage.toString());

		
		// delete ConsensusReferendum if the referendum is aborted, else clean it
		if(((decision == null || decision == false) && statusMessage == 2) ||
			statusMessage == 4) {
			// delete ConsensusReferendum
			HttpRequest.deleteConsensusReferendum(
					referendum.getId().getTitle(),
					referendum.getId().getDateStartConsensusProposal(),
					this.resourceMapping);
		} else {
			// clean
			ConsensusReferendum newConsensusReferendum = new ConsensusReferendum(
					referendum.getId().getTitle(),
					referendum.getId().getDateStartConsensusProposal(),
					4,
					resourceMapping.getListNations()
			);
			HttpRequest.putConsensusReferendum(newConsensusReferendum, resourceMapping);
		}
		
		// update Referendum
		referendum.setStatusByProposalConsensusDecision(decision);
		HttpRequest.putReferendum(referendum, resourceMapping);
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public RabbitTemplate getRabbitTemplate() {
		return this.rabbitTemplate;
	}

	public ResourceMapping getResourceMapping() {
		return this.resourceMapping;
	}

}
