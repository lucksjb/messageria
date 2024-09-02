package com.example.messageria.publishers;

import java.util.Map;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RequestSQS {
    private Map<String, MessageAttributeValue> messageAttributes;
    private String queueName;
    private int delaySeconds;
    private String message;
}