package com.quarkus.developers.services.messaging.emitters;

import com.quarkus.common.data.events.ClusterResourceEvent;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "messaging.emit-to.noOps", stringValue = "true")
public class NoOpsEmitter implements ManagedEmitter {
    @Override
    public void emit(ClusterResourceEvent event) {
        event.setCreated(OffsetDateTime.now());
        event.setProducer("NO-OPS");
        log.info("NoOps | Event {}", event);
    }
}
