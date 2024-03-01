package com.quarkus.developers.services.watching.watchers;

import com.quarkus.developers.dtos.PodDto;
import com.quarkus.developers.mappers.PodEventMapper;
import com.quarkus.developers.services.watching.WatchNotifier;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "watcher.pod.enabled", stringValue = "true")
class PodWatcher implements Watcher<Pod> {
    private final WatchNotifier notifier;
    private final PodEventMapper mapper;

    public PodWatcher(WatchNotifier notifier, PodEventMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
        log.info("New PodWatcher created");
    }

    @Override
    public void eventReceived(Action action, Pod pod) {
        PodDto dto = mapper.toDto(pod);
        dto.setActionName(action.name());
        notifier.notifyWatchedResource(dto);
    }

    @Override
    public void onClose(WatcherException e) {
        log.error("Closing PodsWatcher Watcher", e);
    }

    @Override
    public void onClose() {
        log.info("Gracefully PodsWatcher Shutdown");
    }
}
