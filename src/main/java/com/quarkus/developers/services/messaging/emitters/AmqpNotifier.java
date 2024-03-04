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
@IfBuildProperty(name = "messaging.notify-to.amqp", stringValue = "true")
public class AmqpNotifier implements ManagedEmitter {

    @OnOverflow(OnOverflow.Strategy.DROP)
    @Channel("sr.amqp.channel")
    Emitter<ClusterResourceEvent> amqpEmitter;

    @PostConstruct
    void init() {
        log.info("--> AmqpNotifier ready");
    }

    @Override
    public void emit(ClusterResourceEvent event) {
        event.setCreated(OffsetDateTime.now());
        event.setProducer("AMQP");

        Message<ClusterResourceEvent> message = Message.of(event, () -> {
            log.debug("AMQP Emitter || Acknowledged event {}", event);
            // Called when the message is acknowledged.
            return CompletableFuture.completedFuture(null);
        },
        reason -> {
            log.error("AMQP Emitter || Error during emitting event {} on AMQP {}", event, reason.getLocalizedMessage(), reason);
            // Called when the message is acknowledged negatively.
            return CompletableFuture.completedFuture(null);
        });

        if(amqpEmitter.hasRequests()) {
            log.info("AMQP Emitter || Emitting event on AMQP Channel {}", event);
            amqpEmitter.send(message);
        } else {
            log.warn("AMQP Emitter || Unable to emit event on AMQP Channel because no subscribers found {}", event);
        }
    }
}
