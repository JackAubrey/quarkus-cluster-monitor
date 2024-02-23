package com.quarkus.developers.services.messaging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoOpsProducer implements MessagingProducer {
    @Override
    public void sendMessage(Object message) {
        sendMessage(DEFAULT_QUEUE_NAME, message);
    }

    @Override
    public void sendMessage(String queueName, Object message) {
      log.info("Sending Message {} on Queue {}", message, queueName);
    }
}
