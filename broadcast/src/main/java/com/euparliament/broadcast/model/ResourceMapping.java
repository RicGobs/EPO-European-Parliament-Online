package com.euparliament.broadcast.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapping {
	
	String restUrl;
    String urlReferendum;
    String urlConsensusReferendum;
    String queueName;
	
    public ResourceMapping(@Value("${rest.url}") String restUrl, @Value("${queue.name}") String queueName) {
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
    	return queueName;
    }
    
}