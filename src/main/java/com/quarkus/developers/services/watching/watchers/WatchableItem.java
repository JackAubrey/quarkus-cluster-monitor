package com.quarkus.developers.services.watching.watchers;

import io.fabric8.openshift.client.OpenShiftClient;

import java.util.List;
import java.util.Optional;

public interface WatchableItem {

    Optional<List<WatchedItem>> startWatching(OpenShiftClient client);
}
