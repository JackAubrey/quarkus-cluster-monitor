package com.quarkus.developers.services;

import com.quarkus.developers.events.ResourceQuotaInfo;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.openshift.api.model.Project;

import java.util.List;

public interface MonitorFacade {
    List<Namespace> listNamespaces();

    List<Project> listProjects();

    List<Pod> listPods();
    List<Pod> listPods(String namespace);

    List<ResourceQuotaInfo> listResourceQuotas();

    List<ResourceQuotaInfo> listResourceQuotas(String namespace);
}
