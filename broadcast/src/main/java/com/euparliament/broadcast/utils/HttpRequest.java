package com.euparliament.broadcast.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import com.euparliament.broadcast.model.ConsensusReferendum;
import com.euparliament.broadcast.model.Referendum;
import com.euparliament.broadcast.model.ResourceMapping;

public class HttpRequest {
	public static ConsensusReferendum getConsensusReferendum(String title, String dateStart, ResourceMapping resourceMapping) {
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
		
		HttpEntity<ConsensusReferendum> response = resourceMapping.getRestTemplate().exchange(
		        urlTemplate,
		        HttpMethod.GET,
		        entity,
		        ConsensusReferendum.class,
		        params
		);
		return response.getBody();
	}
	
	public static Referendum getReferendum(String title, String dateStartConsensusProposal, ResourceMapping resourceMapping) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getUrlConsensusReferendum())
		        .queryParam("title", "{title}")
		        .queryParam("dateStartConsensusProposal", "{dateStartConsensusProposal}")
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("title", title);
		params.put("dateStartConsensusProposal", dateStartConsensusProposal);
		
		HttpEntity<Referendum> response = resourceMapping.getRestTemplate().exchange(
		        urlTemplate,
		        HttpMethod.GET,
		        entity,
		        Referendum.class,
		        params
		);
		return response.getBody();
	}
}