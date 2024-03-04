package com.quarkus.developers.services.messaging.emitters;

import com.quarkus.common.data.events.ClusterResourceEvent;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
abstract class AbstractChannelEmitter implements ManagedEmitter {

    @Override
    public void emit(ClusterResourceEvent event) {
        event.setCreated(OffsetDateTime.now());
        event.setProducer(getProducerName());

        Message<ClusterResourceEvent> message = Message.of(event, () -> {
            log.debug("{} Emitter || Acknowledged event {}", getProducerName(), event);
            // Called when the message is acknowledged.
            return CompletableFuture.completedFuture(null);
        },
        reason -> {
            log.error("{} Emitter || Unable to emit the event {} because an error occurred {}", getProducerName(), event, reason.getLocalizedMessage(), reason);
            // Called when the message is acknowledged negatively.
            return CompletableFuture.completedFuture(null);
        });

        if(getEmitter().hasRequests()) {
            log.info("{} Emitter || Emitting event {}", getProducerName(), event);
            getEmitter().send(message);
        } else {
            log.warn("{} Emitter || Unable to emit event because no subscribers found {}", getProducerName(), event);
        }
    }

    protected abstract Emitter<ClusterResourceEvent> getEmitter();

    protected abstract String getProducerName();
}