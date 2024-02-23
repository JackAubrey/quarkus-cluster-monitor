package com.quarkus.developers.services.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quarkus.developers.util.AssertUtil;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JmsProducer implements MessagingProducer {
    private final ConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(Object message) {
        sendMessage(DEFAULT_QUEUE_NAME, message);
    }

    @Override
    public void sendMessage(String queueName, Object message) {
        AssertUtil.hasText(queueName, "The queue name can not be blank");
        AssertUtil.notNull(message, "The message can not be null");

        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)){
            Message msg = context.createTextMessage(objectMapper.writeValueAsString(message));

            log.debug("Sending message {} on the queue {} ", msg.getBody(String.class), queueName);
            context.createProducer().send(context.createQueue(queueName), msg);
        } catch (Exception ex) {
            log.error("Unable to send message to JMS queue {}", queueName, ex);
        }
    }
}
