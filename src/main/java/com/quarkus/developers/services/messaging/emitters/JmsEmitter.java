package com.quarkus.developers.services.messaging.emitters;

import com.quarkus.common.data.events.ClusterResourceEvent;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "messaging.emit-to.jms", stringValue = "true")
public class JmsEmitter extends AbstractChannelEmitter {
    static final String EMITTER_NAME = "JMS";

    @OnOverflow(OnOverflow.Strategy.DROP)
    @Channel("jms-channel")
    Emitter<ClusterResourceEvent> emitter;

    @PostConstruct
    void init() {
        log.info("--> JmsNotifier ready");
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
