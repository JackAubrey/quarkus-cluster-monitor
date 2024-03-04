package com.quarkus.developers.services.watching;

import com.quarkus.common.data.dtos.DeploymentDto;
import com.quarkus.common.data.dtos.PodDto;
import com.quarkus.common.data.dtos.ResourceQuotaDto;
import com.quarkus.common.data.dtos.ServiceDto;

public interface WatchNotifier {

    void notifyWatchedResource(ResourceQuotaDto dto);

    void notifyWatchedResource(DeploymentDto dto);

    void notifyWatchedResource(PodDto dto);

    void notifyWatchedResource(ServiceDto dto);
}
