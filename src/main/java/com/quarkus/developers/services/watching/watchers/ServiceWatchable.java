package com.quarkus.developers.services.watching.watchers;

import io.fabric8.kubernetes.client.Watch;
import io.fabric8.openshift.client.OpenShiftClient;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "watcher.service.enabled", stringValue = "true")
class ServiceWatchable implements WatchableItem {
    final ServiceWatcher serviceWatcher;
    @ConfigProperty(name = "watcher.service.namespaces")
    List<String> nameSpaces;

    public ServiceWatchable(ServiceWatcher serviceWatcher) {
        this.serviceWatcher = serviceWatcher;
        log.info("New ServiceWatchable created");
    }

    @Override
    public Optional<List<WatchedItem>> startWatching(OpenShiftClient client) {
        if(nameSpaces != null && !nameSpaces.isEmpty()) {
            List<WatchedItem> list = new ArrayList<>();
            nameSpaces.forEach(nameSpace -> {
                log.info(">> Going to watch Service for the namespace: {}", nameSpace);
                Watch watched = client.services().inNamespace(nameSpace).watch(serviceWatcher);
                list.add(new WatchedItem(nameSpace, watched));
            });
            return Optional.of(list);
        } else {
            log.warn("No namespaces declared on watcher.service.namespaces property.");
            return Optional.empty();
        }
    }
}
