package com.quarkus.developers.services.watching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quarkus.common.data.dtos.DeploymentDto;
import com.quarkus.common.data.dtos.PodDto;
import com.quarkus.common.data.dtos.ResourceQuotaDto;
import com.quarkus.common.data.dtos.ServiceDto;
import com.quarkus.developers.exceptions.WatchNotifierException;
import com.quarkus.developers.services.messaging.emitters.ManagedEmitter;
import com.quarkus.common.data.events.ClusterResourceEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class WatchNotifierImpl implements WatchNotifier {
    final Instance<ManagedEmitter> instances;
    final ObjectMapper objectMapper;

    public void notifyWatchedResource(ResourceQuotaDto dto) {
        convertAndSend(dto);
    }

    public void notifyWatchedResource(DeploymentDto dto) {
        convertAndSend(dto);
    }

    public void notifyWatchedResource(PodDto dto) {
        convertAndSend(dto);
    }

    public void notifyWatchedResource(ServiceDto dto) {
        convertAndSend(dto);
    }

    void convertAndSend(Object object) {
        if(instances.isUnsatisfied()) {
            log.warn(">>>>>>>>> No emitters found. The message will not emitted: {}", object);
        } else {
            instances.forEach(e -> e.emit(convert(object)));
        }
    }

    ClusterResourceEvent convert(Object object) {
        try {
            Map<String, Object> content = objectMapper.convertValue(object, new TypeReference<>() {});
            ClusterResourceEvent event = new ClusterResourceEvent();
            event.setPayloadContent(content);
            event.setPayloadClass(object.getClass().getCanonicalName());
            return event;
        } catch (Exception e) {
            throw new WatchNotifierException("unable to convert object to ClusterResourceEvent", e);
        }
    }
}
