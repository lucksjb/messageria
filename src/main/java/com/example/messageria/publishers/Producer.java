package com.example.messageria.publishers;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Producer {
    private final AmazonSQS amazonSQS;

    public Producer(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    public void sendMessage(RequestSQS requestSQS) {
        try {
            SendMessageRequest request = new SendMessageRequest()
                    .withQueueUrl(requestSQS.getQueueName())
                    .withMessageBody(requestSQS.getMessage())
                    .withMessageAttributes(requestSQS.getMessageAttributes())
                    .withDelaySeconds(requestSQS.getDelaySeconds());

            String messageId = amazonSQS.sendMessage(request).getMessageId();

            log.info(messageId);
        } catch (Exception exc) {
            throw new RuntimeException(exc.getMessage());
        }
    }

}
