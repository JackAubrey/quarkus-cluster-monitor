package com.quarkus.developers.services.messaging.emitters;

import com.quarkus.developers.services.messaging.events.ClusterResourceEvent;

public interface ManagedEmitter {
    void emit(ClusterResourceEvent event);
}
