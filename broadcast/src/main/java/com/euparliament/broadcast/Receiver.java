package com.euparliament.broadcast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.euparliament.broadcast.model.ConsensusReferendum;
import com.euparliament.broadcast.model.ConsensusReferendumId;
import com.euparliament.broadcast.model.Referendum;
import com.euparliament.broadcast.model.ReferendumMessage;
import com.euparliament.broadcast.model.ResourceMapping;

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

		} catch (Exception e) {
			
			ReferendumMessage referendumMessage = ReferendumMessage.toReferendumMessage(message);
			
			switch(referendumMessage.getStatus()){
				case 2:         
				    // Update the first consensus in the database

					// get consensus data structure from the database
					HttpHeaders headers = new HttpHeaders();
					headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
					HttpEntity<?> entity = new HttpEntity<>(headers);
					
					String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getUrlConsensusReferendum())
							.queryParam("title", "{title}")
							.queryParam("dateStart", "{dateStart}")
							.encode()
							.toUriString();
					Map<String, String> params = new HashMap<>();
					params.put("title", referendumMessage.getTitle());
					params.put("dateStart", referendumMessage.getDateStartConsensusProposal());
					
					HttpEntity<ConsensusReferendum> response = resourceMapping.getRestTemplate().exchange(
							urlTemplate,
							HttpMethod.GET,
							entity,
							ConsensusReferendum.class,
							params
					);
					System.out.println("consensusReferendum GET : " + response.getBody());

                    //change value
					ConsensusReferendum consensusReferendum = response.getBody(); 
					consensusReferendum.setCorrect("RICCARDO HAS MODIFIED IT, YOU ARE REALLY COOL");
					HttpEntity<ConsensusReferendum> consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);

		    		// put new values in the database
		    		ResponseEntity<String> productCreateResponse2 = resourceMapping.getRestTemplate().exchange(resourceMapping.getUrlConsensusReferendum(), HttpMethod.PUT, consensusReferendumEntity, String.class); 
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

					// get consensus data structure from the database
					ConsensusReferendumId consensusReferendumId4 = new ConsensusReferendumId(referendumMessage.getTitle(), referendumMessage.getDateStartConsensusProposal());
					ConsensusReferendum consensusReferendum4 = new ConsensusReferendum();
					consensusReferendum4.setId(consensusReferendumId4);
					HttpEntity<ConsensusReferendum> consensusReferendumEntity4 = new HttpEntity<ConsensusReferendum>(consensusReferendum4);
					
					ResponseEntity<String> response4 = resourceMapping.getRestTemplate().exchange(resourceMapping.getUrlConsensusReferendum(), HttpMethod.GET, consensusReferendumEntity4, String.class); 
				    String body = response4.getBody(); 
                    System.out.println("DONE THE GET");

                    //change value
					consensusReferendum = ConsensusReferendum.toConsensusReferendum(body);
					consensusReferendum.setCorrect("RICCARDO HAS MODIFIED IT, YOU ARE REALLY COOL");
					consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);
		    		
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
