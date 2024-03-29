package com.quarkus.developers.services.watching;

import com.quarkus.developers.services.watching.watchers.WatchableItem;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.openshift.client.OpenShiftClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class WatcherServiceContainer {
    private final OpenShiftClient client;

    final Map<String, List<Watch>> map = new ConcurrentHashMap<>();
    final Instance<WatchableItem> instances;
    @ConfigProperty(name = "watcher.initial-delay.seconds", defaultValue = "10")
    int initialDelayInSeconds;

    @ConfigProperty(name = "watcher.defer.watchers.init.in-seconds", defaultValue = "1")
    int deferWatchersInSec;

    void onStart(@Observes StartupEvent ev) {
        log.info(">>>>>>>>>>>>>>>>>>>>> Quarkus is ready.");
        log.info(">>>>>>>>>>>>>>>>>>>>> The Watchable Items will be initialized in {} seconds", initialDelayInSeconds);
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync( () -> deferredOps(initialDelayInSeconds));

        future.thenAccept(result -> {
            log.info(">>>>>>>>>>>>>>>>>>>>> The Watchable Items has been initialized: {}", result);
            if(Boolean.TRUE.equals(result)) {
                log.info(">>>>>>>>>>>>>>>>>>>>> Found {} Watchable.", instances.stream().toList().size());
                AtomicInteger defInc = new AtomicInteger(deferWatchersInSec);
                instances.forEach(watchableItem -> deferWatcherInit(watchableItem, defInc.getAndAdd(deferWatchersInSec)));
            } else {
                log.error(">>>>>>>>>>>>>>>>>>>>> Unable to start the Watchable Items ");
            }
        });
    }

    void deferWatcherInit(WatchableItem watchableItem, int delay) {
        log.info(">>> Deferring starting watcher {} in {} seconds", watchableItem.getClass().getName(), delay);
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync( () -> deferredOps(delay));

        future.thenAccept(result -> {
            log.info(">>> Start watcher {} ", watchableItem.getClass().getName());
            if(Boolean.TRUE.equals(result)) {
                watchableItem.startWatching(client)
                        .ifPresent(l -> l.forEach(e -> addWatcher(e.nameSpace(), e.watch())));
            } else {
                log.error(">>> Unable to start watcher {} ", watchableItem.getClass().getName());
            }
        });
    }

    boolean deferredOps(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        return true;
    }

    void addWatcher(String nameSpace, Watch watcher) {
        if(map.containsKey(nameSpace)) {
            map.get(nameSpace).add(watcher);
        } else {
            List<Watch> watchers = new CopyOnWriteArrayList<>();
            watchers.add(watcher);
            map.put(nameSpace, watchers);
        }
    }

    @PreDestroy
    void destroy() {
        map.entrySet().forEach(entry -> {
            try {
                entry.getValue().forEach( w -> {
                    log.info(">>>>>>>>>>>>>>>>>>>>> Watchable Item {} in namespace {}", w.getClass().getName(), entry.getKey());
                    w.close();
                });
            } catch (Exception e) {
                log.error("An error occurred on closing of watcher for namespace {}", entry.getValue(), e);
            }
        });
    }
}

