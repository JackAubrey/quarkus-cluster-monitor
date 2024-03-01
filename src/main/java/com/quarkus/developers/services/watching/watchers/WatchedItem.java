package com.quarkus.developers.services.watching.watchers;


import io.fabric8.kubernetes.client.Watch;

public record WatchedItem(String nameSpace, Watch watch){
}
