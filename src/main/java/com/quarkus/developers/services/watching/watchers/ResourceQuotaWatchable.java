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
@IfBuildProperty(name = "watcher.resource-quota.enabled", stringValue = "true")
class ResourceQuotaWatchable implements WatchableItem {
    final ResourceQuotaWatcher resourceQuotaWatcher;
    @ConfigProperty(name = "watcher.resource-quota.namespaces")
    List<String> nameSpaces;

    public ResourceQuotaWatchable(ResourceQuotaWatcher resourceQuotaWatcher) {
        this.resourceQuotaWatcher = resourceQuotaWatcher;
        log.info("New ResourceQuotaWatchable created");
    }

    @Override
    public Optional<List<WatchedItem>> startWatching(OpenShiftClient client) {
        if(nameSpaces != null && !nameSpaces.isEmpty()) {
            List<WatchedItem> list = new ArrayList<>();
            nameSpaces.forEach(nameSpace -> {
                log.info("Going to watch ResourceQuotas for the namespace: {}", nameSpace);
                Watch watched = client.resourceQuotas().inNamespace(nameSpace).watch(resourceQuotaWatcher);
                list.add(new WatchedItem(nameSpace, watched));
            });
            return Optional.of(list);
        } else {
            log.warn("No namespaces declared on watcher.resource-quota.namespaces property.");
            return Optional.empty();
        }
    }
}
