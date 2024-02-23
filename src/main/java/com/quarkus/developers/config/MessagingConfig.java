package com.quarkus.developers.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quarkus.developers.services.messaging.JmsProducer;
import com.quarkus.developers.services.messaging.NoOpsProducer;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.jms.ConnectionFactory;
import jakarta.ws.rs.Produces;

@Dependent
public class MessagingConfig {
    @Produces
    @IfBuildProperty(name = "monitor.producer.type", stringValue = "jms")
    @ApplicationScoped
    JmsProducer jmsProducer(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        return new JmsProducer(connectionFactory, objectMapper);
    }

    @Produces
    @DefaultBean
    @ApplicationScoped
    NoOpsProducer noOpsProducer() {
        return new NoOpsProducer();
    }
}
