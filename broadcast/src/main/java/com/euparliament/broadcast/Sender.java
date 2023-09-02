package com.euparliament.broadcast;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.euparliament.broadcast.model.Referendum;

@Component
@RestController
public class Sender {

  private final RabbitTemplate rabbitTemplate;

  public Sender(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  //for status 1 - messages for knowing the proposals
  @PostMapping("/europeanReferendumProposal")
  public String sendEuropeanReferendumProposal(@RequestBody Referendum referendum) {
    rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendum.toString());
    return "Message sent: " + referendum.toString();
  }

  //for status 2 - messages for first consensus
  @PostMapping("/europeanReferendumFirstConsensus")
  public String sendEuropeanReferendumAnswer(@RequestBody Referendum referendum) {
    referendum.setStatus(2);
    referendum.setAnswer(true);
    rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendum.toString());
    return "Message sent: " + referendum.toString();
  }

  //for status 3 - messages for knowing that a Nation is doing the Referendum (depug purpose)
  @PostMapping("/europeanReferendumDoing")
  public String sendEuropeanReferendumDoing(@RequestBody Referendum referendum) {
    referendum.setStatus(3);
    rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendum.toString());
    return "Message sent: " + referendum.toString();
  }

  //for status 4 - messages for second consensus
  @PostMapping("/europeanReferendumSecondConsensus")
  public String sendEuropeanReferendumResult(@RequestBody Referendum referendum) {
    referendum.setStatus(4);
    rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendum.toString());
    return "Message sent: " + referendum.toString();
  }


}