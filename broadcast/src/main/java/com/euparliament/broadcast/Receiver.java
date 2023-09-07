package com.euparliament.broadcast;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.euparliament.broadcast.model.ConsensusReferendum;
import com.euparliament.broadcast.model.ConsensusReferendumId;
import com.euparliament.broadcast.model.Referendum;
import com.euparliament.broadcast.model.ReferendumMessage;
import com.euparliament.broadcast.model.ResourceMapping;
import com.google.gson.JsonSyntaxException;

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
    				referendum.getTitle(),
    				referendum.getDateStartConsensusProposal(),
    				2 // first consensus
    		);
    		HttpEntity<ConsensusReferendum> consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);
    		String response2 = restTemplate.postForObject(resourceMapping.getUrlConsensusReferendum(), consensusReferendumEntity, String.class);
    		System.out.println("First consensusReferendum created : " + response2);

		} catch (JsonSyntaxException e) {
			
			ReferendumMessage referendumMessage = ReferendumMessage.toReferendumMessage(message);
			
			switch(referendumMessage.getStatus()){
				case 2:         
				    // Update the first consensus in the database

					// get consensus data structure from the database
					ConsensusReferendumId consensusReferendumId = new ConsensusReferendumId(referendumMessage.getTitle(), referendumMessage.getDateStartConsensusProposal());
					ConsensusReferendum consensusReferendum = new ConsensusReferendum();
					consensusReferendum.setId(consensusReferendumId);
					HttpEntity<ConsensusReferendum> consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);
					
					ResponseEntity<String> response = resourceMapping.getRestTemplate().exchange(resourceMapping.getUrlConsensusReferendum(), HttpMethod.GET, consensusReferendumEntity, String.class); 
				    response.getBody(); 
                    System.out.println("DONE THE GET");
		    		// put new values in the database
		    		ResponseEntity<String> productCreateResponse2 = resourceMapping.getRestTemplate().exchange(resourceMapping.getUrlConsensusReferendum(), HttpMethod.PUT, consensusReferendumEntity, String.class); 
				    System.out.println("Changed in : " + productCreateResponse2);
		    		
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

					// get consensus data structure from the database
					ConsensusReferendumId consensusReferendumId4 = new ConsensusReferendumId(referendumMessage.getTitle(), referendumMessage.getDateStartConsensusProposal());
					ConsensusReferendum consensusReferendum4 = new ConsensusReferendum();
					consensusReferendum4.setId(consensusReferendumId4);
					HttpEntity<ConsensusReferendum> consensusReferendumEntity4 = new HttpEntity<ConsensusReferendum>(consensusReferendum4);
					
					ResponseEntity<String> response4 = resourceMapping.getRestTemplate().exchange(resourceMapping.getUrlConsensusReferendum(), HttpMethod.GET, consensusReferendumEntity4, String.class); 
				    response4.getBody(); 

		    		// put new values in the database
		    		ResponseEntity<String> productCreateResponse4 = resourceMapping.getRestTemplate().exchange(resourceMapping.getUrlConsensusReferendum(), HttpMethod.PUT, consensusReferendumEntity4, String.class); 
				    System.out.println("Changed in : " + productCreateResponse4);

		    		latch.countDown();
					break;
	
				default:
					break;
			}
		}
		
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
