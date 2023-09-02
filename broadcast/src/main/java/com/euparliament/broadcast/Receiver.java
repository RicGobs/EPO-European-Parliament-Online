package com.euparliament.broadcast;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.euparliament.broadcast.model.Referendum;

@Component
public class Receiver {

	private String restUrl;
	
	public Receiver(@Value("${rest.url}") String restUrl) {
		this.restUrl = restUrl;
	}
 	
	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		
		Referendum referendum = Referendum.toReferendum(message);
        //there is an error -> not all coming messages are Referendum Class

		switch(referendum.getStatus()){

			case 1:
			    String resourceUrl1 = restUrl + "/referendum";

	    		HttpEntity<Referendum> request1 = new HttpEntity<Referendum>(referendum);
	
	    		RestTemplate restTemplate1 = new RestTemplate();
	    		String productCreateResponse1 = restTemplate1.postForObject(resourceUrl1, request1, String.class);
	    
	    		System.out.println("Received : " + productCreateResponse1);
	    		latch.countDown();
				break;

			case 2:
			    //take the post of rest/consensusReferendum for change values of first consensus in the database 
				// use restTemplate
				String resourceUrl2 = restUrl + "/firstConsensusReferendum";

	    		HttpEntity<Referendum> request2 = new HttpEntity<Referendum>(referendum);
	
	    		RestTemplate restTemplate2 = new RestTemplate();
	    		String productCreateResponse2 = restTemplate2.postForObject(resourceUrl2, request2, String.class);
	    
	    		System.out.println("Received : " + productCreateResponse2);
	    		latch.countDown();
				break;

			case 3:
			    
			    //take the post of broadcast/europeanReferendumAnswer 
				// use restTemplate
				String resourceUrl3 = restUrl + "/consensusReferendumDoing";

	    		HttpEntity<Referendum> request3 = new HttpEntity<Referendum>(referendum);
	
	    		RestTemplate restTemplate3 = new RestTemplate();
	    		String productCreateResponse3 = restTemplate3.postForObject(resourceUrl3, request3, String.class);
	    
	    		System.out.println("Received : " + productCreateResponse3);
	    		latch.countDown();
				break;

			case 4:
			    //take the post of rest/consensusReferendumResult for change values of second consensus in the database 
				// use restTemplate
				String resourceUrl4 = restUrl + "/secondConsensusReferendum";

	    		HttpEntity<Referendum> request4 = new HttpEntity<Referendum>(referendum);
	
	    		RestTemplate restTemplate4 = new RestTemplate();
	    		String productCreateResponse4 = restTemplate4.postForObject(resourceUrl4, request4, String.class);
	    
	    		System.out.println("Received : " + productCreateResponse4);
	    		latch.countDown();
				break;

			default:
				break;
		}
		
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
