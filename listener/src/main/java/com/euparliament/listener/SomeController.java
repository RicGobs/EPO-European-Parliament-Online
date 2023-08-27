package com.euparliament.listener;

import org.springframework.amqp.rabbit.connection.SimpleResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SomeController {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/toA/{message}")
    void sendMessageToA(@PathVariable String message) {
        SimpleResourceHolder.bind(rabbitTemplate.getConnectionFactory(), "connectionNameA");
        try {
            rabbitTemplate.convertAndSend("someExchange", "someRoutingKey", message); // Use RabbitTemplate
        } finally {
            SimpleResourceHolder.unbind(rabbitTemplate.getConnectionFactory());
        }
    }

}
