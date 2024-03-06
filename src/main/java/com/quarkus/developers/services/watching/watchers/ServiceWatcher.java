package com.quarkus.developers.services.watching.watchers;

import com.quarkus.common.data.dtos.ServiceDto;
import com.quarkus.developers.mappers.ServiceMapper;
import com.quarkus.developers.services.watching.WatchNotifier;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "watcher.service.enabled", stringValue = "true")
class ServiceWatcher implements Watcher<Service> {
    private final WatchNotifier notifier;
    private final ServiceMapper mapper;

    public ServiceWatcher(WatchNotifier notifier, ServiceMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
        log.info("New ServicesWatcher created");
    }

    @Override
    public void eventReceived(Action action, Service service) {
        ServiceDto dto = mapper.toDto(service);
        dto.setActionName(action.name());
        notifier.notifyWatchedResource(dto);
    }

    @Override
    public void onClose(WatcherException e) {
        log.error("Closing ServicesWatcher Watcher", e);
    }

    @Override
    public void onClose() {
        log.info("Gracefully ServicesWatcher Shutdown");
    }
}
