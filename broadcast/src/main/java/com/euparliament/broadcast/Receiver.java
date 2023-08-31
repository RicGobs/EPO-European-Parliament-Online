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
		
	    String resourceUrl = restUrl + "/referendum";

	    HttpEntity<Referendum> request = new HttpEntity<Referendum>(referendum);
	
	    RestTemplate restTemplate = new RestTemplate();
	    String productCreateResponse = restTemplate
	               .postForObject(resourceUrl, request, String.class);
	    
	    System.out.println("Received : " + productCreateResponse);
	    latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
