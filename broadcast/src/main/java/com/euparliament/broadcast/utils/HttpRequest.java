package com.euparliament.broadcast.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import com.euparliament.broadcast.exception.NotFoundException;
import com.euparliament.broadcast.model.ConsensusReferendum;
import com.euparliament.broadcast.model.Referendum;
import com.euparliament.broadcast.model.ResourceMapping;

public class HttpRequest {
	public static ConsensusReferendum getConsensusReferendum(String title, String dateStart, ResourceMapping resourceMapping) 
			throws NotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getUrlConsensusReferendum())
		        .queryParam("title", "{title}")
		        .queryParam("dateStart", "{dateStart}")
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("title", title);
		params.put("dateStart", dateStart);
		
		try {
			ResponseEntity<ConsensusReferendum> response = resourceMapping.getRestTemplate().exchange(
			        urlTemplate,
			        HttpMethod.GET,
			        entity,
			        ConsensusReferendum.class,
			        params
			);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			// check if the ConsensusReferendum exists
			throw new NotFoundException();
		}
	}
	
	public static Referendum getReferendum(String title, String dateStartConsensusProposal, ResourceMapping resourceMapping) 
			throws NotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getUrlReferendum())
		        .queryParam("title", "{title}")
		        .queryParam("dateStartConsensusProposal", "{dateStartConsensusProposal}")
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("title", title);
		params.put("dateStartConsensusProposal", dateStartConsensusProposal);
		
		ResponseEntity<Referendum> response = resourceMapping.getRestTemplate().exchange(
		        urlTemplate,
		        HttpMethod.GET,
		        entity,
		        Referendum.class,
		        params
		);
		// check if the ConsensusReferendum exists
		if(response.getStatusCode().value() == 404) {
			throw new NotFoundException();
		}
		return response.getBody();
	}
	
	public static void putConsensusReferendum(ConsensusReferendum consensusReferendum, ResourceMapping resourceMapping) {
		HttpEntity<ConsensusReferendum> consensusReferendumEntity = new HttpEntity<ConsensusReferendum>(consensusReferendum);
		resourceMapping.getRestTemplate().exchange(
				resourceMapping.getUrlConsensusReferendum(), 
				HttpMethod.PUT, 
				consensusReferendumEntity, 
				String.class); 
	}
	
	public static void putReferendum(Referendum referendum, ResourceMapping resourceMapping) {
		HttpEntity<Referendum> referendumEntity = new HttpEntity<Referendum>(referendum);
		resourceMapping.getRestTemplate().exchange(
				resourceMapping.getUrlReferendum(), 
				HttpMethod.PUT, 
				referendumEntity, 
				String.class);
	}
	
	public static void deleteConsensusReferendum(String title, String dateStart, ResourceMapping resourceMapping) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getUrlConsensusReferendum())
		        .queryParam("title", "{title}")
		        .queryParam("dateStart", "{dateStart}")
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("title", title);
		params.put("dateStart", dateStart);
		
		resourceMapping.getRestTemplate().exchange(
		        urlTemplate,
		        HttpMethod.DELETE,
		        entity,
		        ConsensusReferendum.class,
		        params
		);
	}
}