package com.quarkus.developers.services.messaging.emitters;

import com.quarkus.common.data.events.ClusterResourceEvent;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "messaging.notify-to.jms", stringValue = "true")
public class JmsNotifier implements ManagedEmitter {
    @OnOverflow(OnOverflow.Strategy.DROP)
    @Channel("sr.jms.channel")
    Emitter<ClusterResourceEvent> jmsEmitter;

    @PostConstruct
    void init() {
        log.info("--> JmsNotifier ready");
    }

    @Override
    public void emit(ClusterResourceEvent event) {
        event.setCreated(OffsetDateTime.now());
        event.setProducer("JMS");

        Message<ClusterResourceEvent> message = Message.of(event, () -> {
                    log.debug("JMS Emitter || Acknowledged event {}", event);
                    // Called when the message is acknowledged.
                    return CompletableFuture.completedFuture(null);
                },
                reason -> {
                    log.error("JMS Emitter || Error during emitting event {} on JMS {}", event, reason.getLocalizedMessage(), reason);
                    // Called when the message is acknowledged negatively.
                    return CompletableFuture.completedFuture(null);
                });

        if(jmsEmitter.hasRequests()) {
            log.info("JMS Emitter || Emitting event on JMS Channel {}", event);
            jmsEmitter.send(message);
        } else {
            log.warn("JMS Emitter || Unable to emit event on JMS Channel because no subscribers found {}", event);
        }
    }
}
