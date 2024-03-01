package com.quarkus.developers.services;

import com.quarkus.developers.dtos.PodDto;
import com.quarkus.developers.dtos.ResourceQuotaDto;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.openshift.api.model.Project;

import java.util.List;

public interface MonitorFacade {
    List<Namespace> listNamespaces();

    List<Project> listProjects();

    List<PodDto> listPods();
    List<PodDto> listPods(String namespace);

    List<ResourceQuotaDto> listResourceQuotas();

    List<ResourceQuotaDto> listResourceQuotas(String namespace);
}
