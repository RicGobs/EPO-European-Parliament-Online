package com.euparliament.broadcast;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.euparliament.broadcast.model.ConsensusReferendum;
import com.euparliament.broadcast.model.Referendum;
import com.euparliament.broadcast.model.ReferendumMessage;
import com.euparliament.broadcast.model.ResourceMapping;
import com.euparliament.broadcast.utils.HttpRequest;


@Component
public class Receiver {
	
	RestTemplate restTemplate;
	
	@Autowired
	ResourceMapping resourceMapping;
	
	public Receiver() {
		restTemplate = new RestTemplate();
	}
 	
	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {

		try {
			Referendum referendum = Referendum.toReferendum(message);
			// store the referendum proposal
    		HttpEntity<Referendum> referendumEntity = new HttpEntity<Referendum>(referendum);
    		String response1 = restTemplate.postForObject(resourceMapping.getUrlReferendum(), referendumEntity, String.class);
    		System.out.println("Referendum created : " + response1);
    		
    		// post consensus data structures
    		ConsensusReferendum consensusReferendum = new ConsensusReferendum(
    				referendum.getId().getTitle(),
    				referendum.getId().getDateStartConsensusProposal(),
    				2 // first consensus
    		);
    		HttpEntity<ConsensusReferendum> consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);
    		String response2 = restTemplate.postForObject(resourceMapping.getUrlConsensusReferendum(), consensusReferendumEntity, String.class);
    		System.out.println("First consensusReferendum created : " + response2);

		} catch (Exception e) {
			
			ReferendumMessage referendumMessage = ReferendumMessage.toReferendumMessage(message);
			System.out.println("referendumMessage : " + referendumMessage.toString());
			
			switch(referendumMessage.getStatus()){
				case 2:
					// get consensus data structure from the database
					ConsensusReferendum consensusReferendum = HttpRequest.getConsensusReferendum(
							referendumMessage.getTitle(),
							referendumMessage.getDateStartConsensusProposal(),
							this.resourceMapping);
					System.out.println("consensusReferendum GET : " + consensusReferendum); 

                    //change value
					consensusReferendum.updateProposals(
							referendumMessage.getProposals(), 
							referendumMessage.getRound()
					);
					consensusReferendum.updateRecivedFrom(
							referendumMessage.getNationSourceAnswer(), 
							referendumMessage.getRound()
					);

		    		// put new values in the database
					HttpEntity<ConsensusReferendum> consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);
		    		ResponseEntity<String> productCreateResponse2 = resourceMapping.getRestTemplate().exchange(
		    				resourceMapping.getUrlConsensusReferendum(), 
		    				HttpMethod.PUT, 
		    				consensusReferendumEntity, 
		    				String.class); 
				    System.out.println("consensusReferendum PUT : " + productCreateResponse2);

		    		latch.countDown();
					break;
	
				case 3:
				    
				    //take the post of broadcast/europeanReferendumAnswer 
					// use restTemplate
	
		    		HttpEntity<ReferendumMessage> request3 = new HttpEntity<ReferendumMessage>(referendumMessage);
		
		    		RestTemplate restTemplate3 = new RestTemplate();
		    		String productCreateResponse3 = restTemplate3.postForObject(resourceMapping.getUrlConsensusReferendum(), request3, String.class);
		    
		    		System.out.println("Changed in : " + productCreateResponse3);

		    		latch.countDown();
					break;
	
				case 4:
				    // Update the second consensus in the database
                    
					
	
				default:
					break;
			}
		}
		
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
