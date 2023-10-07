package com.euparliament.web.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import com.euparliament.web.exception.NotFoundException;
import com.euparliament.web.model.CitizenUser;
import com.euparliament.web.model.InstUser;
import com.euparliament.web.model.Referendum;
import com.euparliament.web.model.ResourceMapping;

public class HttpRequest {
	public static CitizenUser getCitizenUser(String nationalID, String password, ResourceMapping resourceMapping) 
			throws NotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getCitizenUrlLogin())
		        .queryParam("uname", "{uname}")
				.queryParam("psw", "{psw}")
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("uname", nationalID);
		params.put("psw", password);

		try {
			ResponseEntity<CitizenUser> response = resourceMapping.getRestTemplate().exchange(
			        urlTemplate,
			        HttpMethod.GET,
			        entity,
			        CitizenUser.class,
			        params
			);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			// check if the CitizenUser exists
			//return null;
			throw new NotFoundException();
		}
	}

	public static boolean checkCitizenUser(String nationalID, ResourceMapping resourceMapping) 
			throws NotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getCitizensUrl() + '/' + nationalID)
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();

		try {
			resourceMapping.getRestTemplate().exchange(
			        urlTemplate,
			        HttpMethod.GET,
			        entity,
			        CitizenUser.class,
			        params
			);
			return true;
		} catch (HttpClientErrorException e) {
			throw new NotFoundException();
		}
	}

	public static InstUser getInstUser(String representativeID, String password, ResourceMapping resourceMapping) 
			throws NotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getRepresentativeUrlLogin())
		        .queryParam("uname", "{uname}")
				.queryParam("psw", "{psw}")
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("uname", representativeID);
		params.put("psw", password);

		try {
			ResponseEntity<InstUser> response = resourceMapping.getRestTemplate().exchange(
			        urlTemplate,
			        HttpMethod.GET,
			        entity,
			        InstUser.class,
			        params
			);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			// check if the InstUser exists
			//return null;
			throw new NotFoundException();
		}
	}

	public static boolean checkInstUser(String representativeID, ResourceMapping resourceMapping) 
			throws NotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getRepresentativesUrl() + '/' + representativeID)
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();

		try {
			resourceMapping.getRestTemplate().exchange(
			        urlTemplate,
			        HttpMethod.GET,
			        entity,
			        CitizenUser.class,
			        params
			);
			return true;
		} catch (HttpClientErrorException e) {
			throw new NotFoundException();
		}
	}

	public static Referendum getReferendum(String title, String date, ResourceMapping resourceMapping) 
			throws NotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getReferendumUrl())
		        .queryParam("title", "{title}")
				.queryParam("dateStartConsensusProposal", "{dateStartConsensusProposal}")
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("title", title);
		params.put("dateStartConsensusProposal", date);

		try {
			ResponseEntity<Referendum> response = resourceMapping.getRestTemplate().exchange(
			        urlTemplate,
			        HttpMethod.GET,
			        entity,
			        Referendum.class,
			        params
		);
		return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new NotFoundException();
		}

	}
}
