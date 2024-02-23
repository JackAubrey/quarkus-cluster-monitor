package com.quarkus.developers.services;

import com.quarkus.developers.events.ResourceQuotaInfo;
import com.quarkus.developers.mappers.ResourceQuotaInfoMapper;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
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
    private final ResourceQuotaInfoMapper resourceQuotaInfoMapper;

    @Override
    public List<Namespace> listNamespaces() {
        return client.namespaces().list().getItems();
    }

    @Override
    public List<Project> listProjects() {
        return client.projects().list().getItems();
    }

    public List<Pod> listPods() {
        return client.pods().list().getItems();
    }

    @Override
    public List<Pod> listPods(String namespace) {
        return client.pods().inNamespace(namespace).list().getItems();
    }

    @Override
    public List<ResourceQuotaInfo> listResourceQuotas() {
        log.info("Resource Quotas");
        List<ResourceQuotaInfo> list = new ArrayList<>();

        client.resourceQuotas().list().getItems().forEach(quota -> list.add(resourceQuotaInfoMapper.toSimpleQuotaInfo(quota)));

        return list;
    }

    @Override
    public List<ResourceQuotaInfo> listResourceQuotas(String namespace) {
        log.info("Resource Quotas in namespace {}", namespace);
        List<ResourceQuotaInfo> list = new ArrayList<>();

        client.resourceQuotas().inNamespace(namespace).list().getItems().forEach(quota -> list.add(resourceQuotaInfoMapper.toSimpleQuotaInfo(quota)));

        return list;
    }
}
