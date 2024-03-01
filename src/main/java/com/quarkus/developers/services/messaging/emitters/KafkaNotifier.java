package com.quarkus.developers.services.messaging.emitters;

import com.quarkus.developers.services.messaging.events.ClusterResourceEvent;
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
@IfBuildProperty(name = "messaging.notify-to.kafka", stringValue = "true")
public class KafkaNotifier implements ManagedEmitter {
    @OnOverflow(OnOverflow.Strategy.DROP)
    @Channel("sr.kafka.channel")
    Emitter<ClusterResourceEvent> kafkaEmitter;

    @PostConstruct
    void init() {
        log.info("--> KafkaNotifier ready");
    }

    @Override
    public void emit(ClusterResourceEvent event) {
        event.setCreated(OffsetDateTime.now());
        event.setProducer("KAFKA");

        Message<ClusterResourceEvent> message = Message.of(event, () -> {
                    log.debug("Acknowledged event {}", event);
                    // Called when the message is acknowledged.
                    return CompletableFuture.completedFuture(null);
                },
                reason -> {
                    log.error("Error during emitting event {} on kafka {}", event, reason.getLocalizedMessage(), reason);
                    // Called when the message is acknowledged negatively.
                    return CompletableFuture.completedFuture(null);
                });

        if(kafkaEmitter.hasRequests()) {
            log.info("Emitting event on Kafka Channel {}", event);
            kafkaEmitter.send(message);
        } else {
            log.warn("Unable to emit event on Kafka Channel because no subscribers found {}", event);
        }
    }
}
