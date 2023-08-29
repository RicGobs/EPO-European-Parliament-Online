package com.euparliament.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.euparliament.sender.model.EuropeanReferendum;

@Component
@RestController
public class Sender implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;

  public Sender(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Sending message...");
  }

  @PostMapping("/europeanReferendumBroadcast")
  public String sendMessage(@RequestBody EuropeanReferendum referendum) {
    rabbitTemplate.convertAndSend(SenderApplication.topicExchangeName, "foo.bar.baz", referendum.toString());
    return "Message sent: " + referendum.toString();
  }

}