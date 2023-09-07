package com.euparliament.broadcast;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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