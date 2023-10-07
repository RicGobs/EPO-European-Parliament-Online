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
	String citizensUrl;
	String representativesUrl;

    public ResourceMapping(
    		@Value("${rest.url}") String restUrl
    ){
    	this.restTemplate = new RestTemplate();
    	this.restUrl = restUrl;
		this.citizenUrlLogin = restUrl + "/citizenLogin";
		this.representativeUrlLogin = restUrl + "/representativeLogin";
		this.referendumUrl = restUrl + "/referendum";
		this.citizensUrl = restUrl + "/citizens";
		this.representativesUrl = restUrl + "/representatives";

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

	public String getCitizensUrl() {
		return this.citizensUrl;
	}

	public String getRepresentativesUrl() {
		return this.representativesUrl;
	}
	
    public RestTemplate getRestTemplate() {
    	return this.restTemplate;
    }
    
} 
