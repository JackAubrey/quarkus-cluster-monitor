package com.quarkus.developers.services.watching.watchers;

import com.quarkus.common.data.dtos.ResourceQuotaDto;
import com.quarkus.developers.mappers.ResourceQuotaDtoMapper;
import com.quarkus.developers.services.watching.WatchNotifier;
import io.fabric8.kubernetes.api.model.ResourceQuota;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "watcher.resource-quota.enabled", stringValue = "true")
class ResourceQuotaWatcher implements Watcher<ResourceQuota> {
    private final WatchNotifier notifier;
    private final ResourceQuotaDtoMapper mapper;
    public ResourceQuotaWatcher(WatchNotifier notifier, ResourceQuotaDtoMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
        log.info("New ResourceQuotasWatcher created");
    }

    @Override
    public void eventReceived(Action action, ResourceQuota resourceQuota) {
        ResourceQuotaDto event = mapper.toDto(resourceQuota);
        event.setActionName(action.name());
        notifier.notifyWatchedResource(event);
    }

    @Override
    public void onClose(WatcherException e) {
        log.error("Closing ResourceQuota Watcher", e);
    }

    @Override
    public void onClose() {
        log.info("Gracefully ResourceQuota Shutdown");
    }
}
