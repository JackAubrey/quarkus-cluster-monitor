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
@IfBuildProperty(name = "watcher.pod.enabled", stringValue = "true")
class PodWatchable implements WatchableItem {
    final PodWatcher podWatcher;
    @ConfigProperty(name = "watcher.pod.namespaces")
    List<String> nameSpaces;

    public PodWatchable(PodWatcher podWatcher) {
        this.podWatcher = podWatcher;
        log.info("New PodWatchable created");
    }

    @Override
    public Optional<List<WatchedItem>> startWatching(OpenShiftClient client) {
        if(nameSpaces != null && !nameSpaces.isEmpty()) {
            List<WatchedItem> list = new ArrayList<>();
            nameSpaces.forEach(nameSpace -> {
                log.info("Going to watch PODs for the namespace: {}", nameSpace);
                Watch watched = client.pods().inNamespace(nameSpace).withLabelSelector("").watch(podWatcher);
                list.add(new WatchedItem(nameSpace, watched));
            });
            return Optional.of(list);
        } else {
            log.warn("No namespaces declared on watcher.pod.namespaces property.");
            return Optional.empty();
        }
    }
}
