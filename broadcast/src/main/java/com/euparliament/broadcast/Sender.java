package com.euparliament.broadcast;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.euparliament.broadcast.model.ConsensusReferendum;
import com.euparliament.broadcast.model.Referendum;
import com.euparliament.broadcast.model.ReferendumMessage;
import com.euparliament.broadcast.model.ResourceMapping;

@Component
@RestController
public class Sender {

	final RabbitTemplate rabbitTemplate;
	
	@Autowired
	ResourceMapping resourceMapping;

	public Sender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	//for status 1 - messages for knowing the proposals
	@PostMapping("/europeanReferendumProposal")
	public String sendEuropeanReferendumProposal(@RequestBody Referendum referendum) {
		referendum.setNationCreator(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendum.toString());
		return "Message sent: " + referendum.toString();
	}

	//for status 2 - propose value for first consensus
	@PostMapping("/europeanReferendumFirstConsensus")
	public String sendEuropeanReferendumAnswer(@RequestBody ReferendumMessage referendumMessage) {
		// get consensus data structure from the database
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(resourceMapping.getUrlConsensusReferendum())
		        .queryParam("title", "{title}")
		        .queryParam("dateStart", "{dateStart}")
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("title", referendumMessage.getTitle());
		params.put("dateStart", referendumMessage.getDateStartConsensusProposal());
		
		HttpEntity<ConsensusReferendum> response = resourceMapping.getRestTemplate().exchange(
		        urlTemplate,
		        HttpMethod.GET,
		        entity,
		        ConsensusReferendum.class,
		        params
		);
		System.out.println("consensusReferendum: " + response.getBody());
		
		// append this nation to the proposals
		
		
		referendumMessage.setStatus(2);
		referendumMessage.setNationSourceAnswer(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendumMessage.toString());
		return "Message sent: " + referendumMessage.toString();
	}

	//for status 3 - messages for knowing that a Nation is doing the Referendum (depug purpose)
	@PostMapping("/europeanReferendumDoing")
	public String sendEuropeanReferendumDoing(@RequestBody ReferendumMessage referendumMessage) {
		referendumMessage.setStatus(3);
		referendumMessage.setNationSourceAnswer(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendumMessage.toString());
		return "Message sent: " + referendumMessage.toString();
	}

	//for status 4 - messages for second consensus
	@PostMapping("/europeanReferendumSecondConsensus")
	public String sendEuropeanReferendumResult(@RequestBody ReferendumMessage referendumMessage) {
		referendumMessage.setStatus(4);
		referendumMessage.setNationSourceAnswer(resourceMapping.getQueueName());
		rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendumMessage.toString());
		return "Message sent: " + referendumMessage.toString();
	}
}