package com.quarkus.developers.services;

import com.quarkus.common.data.dtos.PodDto;
import com.quarkus.common.data.dtos.ResourceQuotaDto;
import com.quarkus.developers.mappers.PodEventMapper;
import com.quarkus.developers.mappers.ResourceQuotaDtoMapper;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.ResourceQuota;
import io.fabric8.openshift.api.model.Project;
import io.fabric8.openshift.client.OpenShiftClient;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class MonitorFacadeImpl implements MonitorFacade {
    private final OpenShiftClient client;
    private final ResourceQuotaDtoMapper resourceQuotaDtoMapper;
    private final PodEventMapper podEventMapper;

    @Override
    public List<Namespace> listNamespaces() {
        return client.namespaces().list().getItems();
    }

    @Override
    public List<Project> listProjects() {
        return client.projects().list().getItems();
    }

    public List<PodDto> listPods() {
        return convertPods(client.pods().list().getItems());
    }

    @Override
    public List<PodDto> listPods(String namespace) {
        return convertPods(client.pods().inNamespace(namespace).list().getItems());
    }

    @Override
    public List<ResourceQuotaDto> listResourceQuotas() {
        log.info("Resource Quotas");
        return convertResourceQuotas(client.resourceQuotas().list().getItems());
    }

    @Override
    public List<ResourceQuotaDto> listResourceQuotas(String namespace) {
        log.info("Resource Quotas in namespace {}", namespace);
        return convertResourceQuotas(client.resourceQuotas().inNamespace(namespace).list().getItems());
    }

    List<ResourceQuotaDto> convertResourceQuotas(List<ResourceQuota> quotas) {
        if(quotas != null && !quotas.isEmpty()) {
            List<ResourceQuotaDto> list = new ArrayList<>();

            quotas.forEach(quota -> list.add(resourceQuotaDtoMapper.toDto(quota)));

            return list;
        } else {
           return new ArrayList<>();
        }
    }

    List<PodDto> convertPods(List<Pod> pods) {
        if(pods != null && !pods.isEmpty()) {
            List<PodDto> list = new ArrayList<>();

            pods.forEach(pod -> list.add(podEventMapper.toDto(pod)));

            return list;
        } else {
            return new ArrayList<>();
        }
    }
}
