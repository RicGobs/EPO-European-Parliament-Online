package com.euparliament.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("/{message}")
  public String sendMessage(@PathVariable String message) {
    rabbitTemplate.convertAndSend(SenderApplication.topicExchangeName, "foo.bar.baz", message);
    return "Message sent: " + message;
  }

}