package com.quarkus.developers.mappers.decoders;

import io.fabric8.kubernetes.api.model.PodIP;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PodIPDecoder {
    public String asString(PodIP podIP) {
        return podIP == null ? null : podIP.getIp();
    }
}
