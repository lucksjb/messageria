package com.example.messageria.config;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.services.sqs.model.ChangeMessageVisibilityBatchRequest;
import com.amazonaws.services.sqs.model.ChangeMessageVisibilityRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestHandlerSqs extends RequestHandler2 {

    private final int VISIBILITY_TIMEOUT_EXCEPTION = 90;
    private final int VISIBILITY_TIMEOUT_RECEIVED = 300;

    @Override
    public AmazonWebServiceRequest beforeExecution(AmazonWebServiceRequest request) {

        if(request instanceof ReceiveMessageRequest){
            //invoke each received message
            ((ReceiveMessageRequest) request).setVisibilityTimeout(VISIBILITY_TIMEOUT_RECEIVED);
            log.debug("Changed visibility timeout for request {}", request);
        }else if (request instanceof ChangeMessageVisibilityBatchRequest) {
            ((ChangeMessageVisibilityBatchRequest) request).getEntries().forEach(changeMessageVisibilityBatchRequestEntry -> {
                changeMessageVisibilityBatchRequestEntry.setVisibilityTimeout(VISIBILITY_TIMEOUT_EXCEPTION);
            });
            log.debug("Changed entries visibility timeout for request {}", request);
        }else if(request instanceof ChangeMessageVisibilityRequest) {
            //invoke in case of exception
            ((ChangeMessageVisibilityRequest) request).setVisibilityTimeout(VISIBILITY_TIMEOUT_EXCEPTION);
            log.info("visible timeout exception");
        }

        return request;
    }
}
