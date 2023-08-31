package com.euparliament.broadcast;

import java.util.concurrent.CountDownLatch;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Receiver {

  private CountDownLatch latch = new CountDownLatch(1);

  public void receiveMessage(String message) {
    
    String resourceUrl = "http://rest:8080/referendum";

    //WE SHOULD DO A PARSING OF MESSAGE
    HttpEntity<Referendum> request = new HttpEntity<Referendum>(
            new Referendum("Cannabis for Everyone","active"));

    RestTemplate restTemplate = new RestTemplate();
    String productCreateResponse = restTemplate
               .postForObject(resourceUrl, request, String.class);
    
    System.out.println("Received <" + message + "> AND " + productCreateResponse);
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

}
