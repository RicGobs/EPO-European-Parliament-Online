package com.eparliament.broadcast;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eparliament.broadcast.model.EuropeanReferendum;

@Component
@RestController
public class Sender {

  private final RabbitTemplate rabbitTemplate;

  public Sender(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @PostMapping("/europeanReferendumBroadcast")
  public String sendMessage(@RequestBody EuropeanReferendum referendum) {
    rabbitTemplate.convertAndSend(BroadcastApplication.topicExchangeName, "foo.bar.baz", referendum.toString());
    return "Message sent: " + referendum.toString();
  }

}