package com.example.messageria.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messageria.publishers.Producer;
import com.example.messageria.publishers.RequestSQS;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping
public class MessagesController {
    private final Producer producer;
    
    public MessagesController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping
    public void sendMessage(@RequestBody String message) {
        RequestSQS requestSQS = RequestSQS.builder()
        .delaySeconds(0)
        .message(message)
        .queueName("SQS_FILA1")
        .messageAttributes(null)
        .build();
        producer.sendMessage(requestSQS);
    }
}
