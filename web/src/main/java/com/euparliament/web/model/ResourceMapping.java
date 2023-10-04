package com.euparliament.web.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResourceMapping {
	
	RestTemplate restTemplate;
	String restUrl;
	String citizenUrlLogin;
	String representativeUrlLogin;
	String referendumUrl;

    public ResourceMapping(
    		@Value("${rest.url}") String restUrl
    ){
    	this.restTemplate = new RestTemplate();
    	this.restUrl = restUrl;
		this.citizenUrlLogin = restUrl + "/citizenLogin";
		this.representativeUrlLogin = restUrl + "/representativeLogin";
		this.referendumUrl = restUrl + "/referendum";

    }
    
	public String getCitizenUrlLogin() {
		return this.citizenUrlLogin;
	}

	public String getRepresentativeUrlLogin() {
		return this.representativeUrlLogin;
	}

	public String getReferendumUrl() {
		return this.referendumUrl;
	}
	
    public RestTemplate getRestTemplate() {
    	return this.restTemplate;
    }
    
} 