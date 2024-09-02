package com.example.messageria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import jakarta.jms.Session;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@EnableJms
@Configuration
@Slf4j
public class ConfigJMS {

    // @Value("${localStack}")
    private boolean useLocalStack = true;

    // private static final int NUMBER_OF_MESSAGES_TO_PREFETCH = 5;

    @Bean
    public AmazonSQS amazonSqs(final SqsProperties sqsProperties) {
        AmazonSQSAsyncClientBuilder amazonSQS = AmazonSQSAsyncClientBuilder.standard();
        if (useLocalStack) {
            amazonSQS.withEndpointConfiguration(
                    new EndpointConfiguration("http://localhost:4566", sqsProperties.getRegion()));
        } else {
            amazonSQS.withRegion(sqsProperties.getRegion());
        }
        return amazonSQS.withRequestHandlers(new RequestHandlerSqs())
                .build();
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(final SqsProperties sqsProperties) {

        var client = SqsClient.builder()
                .region(Region.of(sqsProperties.getRegion()))
                .credentialsProvider(null)
                .build();

        var sqsConnectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                client);

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("2");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        return factory;
    }

}
