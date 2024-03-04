package com.quarkus.developers.services.watching.watchers;

import com.quarkus.common.data.dtos.DeploymentDto;
import com.quarkus.developers.mappers.DeploymentDtoMapper;
import com.quarkus.developers.services.watching.WatchNotifier;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "watcher.deployment.enabled", stringValue = "true")
class DeploymentWatcher implements Watcher<Deployment> {
    private final WatchNotifier notifier;
    private final DeploymentDtoMapper mapper;

    public DeploymentWatcher(WatchNotifier notifier, DeploymentDtoMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
        log.info("New DeploymentWatcher created");
    }

    @Override
    public void eventReceived(Action action, Deployment deployment) {
        DeploymentDto dto = mapper.toDto(deployment);
        dto.setActionName(action.name());
        notifier.notifyWatchedResource(dto);
    }

    @Override
    public void onClose(WatcherException e) {
        log.error("Closing Deployment Watcher", e);
    }

    @Override
    public void onClose() {
        log.info("Gracefully Deployment Shutdown");
    }
}
