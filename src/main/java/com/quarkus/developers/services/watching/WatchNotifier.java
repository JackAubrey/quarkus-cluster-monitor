package com.quarkus.developers.services.watching;

import com.quarkus.developers.dtos.DeploymentDto;
import com.quarkus.developers.dtos.PodDto;
import com.quarkus.developers.dtos.ResourceQuotaDto;
import com.quarkus.developers.dtos.ServiceDto;

public interface WatchNotifier {

    void notifyWatchedResource(ResourceQuotaDto dto);

    void notifyWatchedResource(DeploymentDto dto);

    void notifyWatchedResource(PodDto dto);

    void notifyWatchedResource(ServiceDto dto);
}
