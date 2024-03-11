package com.quarkus.developers.services.messaging.emitters;

import com.quarkus.common.data.events.ClusterResourceEvent;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.OffsetDateTime;

@Slf4j
@ApplicationScoped
public class NoOpsEmitter implements ManagedEmitter {
    @ConfigProperty(name = "messaging.emit-to.noOps", defaultValue = "false")
    boolean enabled;

    @Override
    public void emit(ClusterResourceEvent event) {
        if(enabled) {
            event.setCreated(OffsetDateTime.now());
            event.setProducer("NO-OPS");
            log.info("NoOps | Event {}", event);
        }
    }
}
