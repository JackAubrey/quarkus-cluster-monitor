package com.quarkus.developers.services.messaging.emitters;

import com.quarkus.common.data.events.ClusterResourceEvent;

public interface ManagedEmitter {
    void emit(ClusterResourceEvent event);
}
