package com.quarkus.developers.services;

import io.fabric8.kubernetes.api.model.ResourceQuota;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.fabric8.openshift.client.OpenShiftClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class WatcherService {
    private final OpenShiftClient client;

    private Map<String, Watch> map = new ConcurrentHashMap<>();

    void onStart(@Observes StartupEvent ev) {
        log.info("Quarkus is ready");
        watchResourceQuotas("butta");
    }

    void watchResourceQuotas(String nameSpace) {
        if( !map.containsKey(nameSpace) ) {
            log.info("Going to watch ResourceQuotas for the namespace: {}", nameSpace);
            Watch watch = client.resourceQuotas().inNamespace(nameSpace).watch(new ResourceQuotasWatcher());
            map.put(nameSpace, watch);
            log.info("Watching resource quotas of {}", nameSpace);
        }
    }

    @PreDestroy
    void destroy() {
        map.entrySet().forEach(entry -> {
            try {
                log.info("Closing watched ResourceQuotas of {}", entry.getKey());
                entry.getValue().close();
            } catch (Exception e) {
                log.error("An error occurred on closing of watcher for namespace {}", entry.getValue(), e);
            }
        });
    }
}

@Slf4j
class ResourceQuotasWatcher implements Watcher<ResourceQuota> {
    public ResourceQuotasWatcher() {
        log.info("New ResourceQuotasWatcher created");
    }

    @Override
    public void eventReceived(Action action, ResourceQuota resourceQuota) {
        log.info("ResourceQuota Action Name: {}", action.name());
        log.info("ResourceQuota Status: {}", resourceQuota.getStatus());
        action.describeConstable().ifPresent(a -> log.info("## ResourceQuota Action Type: {}", a));
        log.info("ResourceQuota Additional Properties: {}", resourceQuota.getAdditionalProperties());
        log.info("ResourceQuota Metadata NameSpace: {}", resourceQuota.getMetadata().getNamespace());
        log.info("ResourceQuota Metadata: {}", resourceQuota.getMetadata());
    }

    @Override
    public void onClose(WatcherException e) {
        log.info("Closing Watcher", e);
    }

    @Override
    public void onClose() {
        log.info("Gracefully shutdown");
    }
}
