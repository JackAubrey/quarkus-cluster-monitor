package com.quarkus.developers.services.messaging.emitters;

import com.quarkus.common.data.events.ClusterResourceEvent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

@Slf4j
@ApplicationScoped
public class AmqpManagedEmitter extends AbstractManagedEmitter {
    static final String EMITTER_NAME = "AMQP";

    @ConfigProperty(name = "messaging.emit-to.amqp", defaultValue = "false")
    boolean enabled;

    @OnOverflow(OnOverflow.Strategy.DROP)
    @Channel("amqp-channel")
    Emitter<ClusterResourceEvent> emitter;

    @PostConstruct
    void init() {
        log.info("--> AmqpNotifier enabled: {}", enabled);
    }


    @Override
    boolean isEnabled() {
        return enabled;
    }

    @Override
    Emitter<ClusterResourceEvent> getEmitter() {
        return emitter;
    }

    @Override
    String getProducerName() {
        return EMITTER_NAME;
    }
}
