package com.example.messageria.listerners;

import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

@Service
public class Listener {
    @JmsListener(destination = "SQS_FILA1")
    public void prepareReport(String message, @Headers Map<String, Object> headers) {
        System.out.println(message);
    }
}
