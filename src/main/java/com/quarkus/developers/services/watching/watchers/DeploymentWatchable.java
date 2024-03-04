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
@IfBuildProperty(name = "watcher.deployment.enabled", stringValue = "true")
class DeploymentWatchable implements WatchableItem {
    final DeploymentWatcher deploymentWatcher;
    @ConfigProperty(name = "watcher.deployment.namespaces")
    List<String> nameSpaces;

    DeploymentWatchable(DeploymentWatcher deploymentWatcher) {
        this.deploymentWatcher = deploymentWatcher;
        log.info("New DeploymentWatchable created");
    }

    @Override
    public Optional<List<WatchedItem>> startWatching(OpenShiftClient client) {
        if(nameSpaces != null && !nameSpaces.isEmpty()) {
            List<WatchedItem> list = new ArrayList<>();
            nameSpaces.forEach(nameSpace -> {
                log.info(">> Going to watch Deployment for the namespace: {}", nameSpace);
                Watch watched = client.apps().deployments().inNamespace(nameSpace).watch(deploymentWatcher);
                list.add(new WatchedItem(nameSpace, watched));
            });
            return Optional.of(list);
        } else {
            log.warn("No namespaces declared on watcher.deployment.namespaces property.");
            return Optional.empty();
        }
    }
}
