package com.euparliament.sender;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SenderApplication {

  static final String topicExchangeName = "referendum-exchange";

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(topicExchangeName);
  }

  public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(SenderApplication.class, args);
  }

}