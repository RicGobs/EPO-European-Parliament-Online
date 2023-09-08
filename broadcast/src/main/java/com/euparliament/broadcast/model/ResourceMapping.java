package com.euparliament.broadcast.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResourceMapping {
	
	RestTemplate restTemplate;
	String restUrl;
    String urlReferendum;
    String urlConsensusReferendum;
    String queueName;
	
    public ResourceMapping(@Value("${rest.url}") String restUrl, @Value("${queue.name}") String queueName) {
    	this.restTemplate = new RestTemplate();
    	this.restUrl = restUrl;
    	this.urlReferendum = restUrl + "/referendum";
    	this.urlConsensusReferendum = restUrl + "/consensusReferendum";
    	this.queueName = queueName;
    }
    
    public String getUrlReferendum() {
    	return this.urlReferendum;
    }
    
    public String getUrlConsensusReferendum() {
    	return this.urlConsensusReferendum;
    }
    
    public String getQueueName() {
    	return this.queueName;
    }
    
    public RestTemplate getRestTemplate() {
    	return this.restTemplate;
    }
    
} 