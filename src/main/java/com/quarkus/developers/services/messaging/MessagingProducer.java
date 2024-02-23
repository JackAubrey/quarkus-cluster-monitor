package com.quarkus.developers.services.messaging;

public interface MessagingProducer {
    String DEFAULT_QUEUE_NAME = "my_default_queue";

    void sendMessage(Object message);
    void sendMessage(String queueName, Object message);
}
