package com.quarkus.developers.services;

import com.quarkus.developers.data.ResourceQuotaInfo;
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

        client.resourceQuotas().list().getItems().forEach(quotas -> list.add(getResourceQuotaInfo(quotas)));

        return list;
    }

    @Override
    public List<ResourceQuotaInfo> listResourceQuotas(String namespace) {
        log.info("Resource Quotas in namespace {}", namespace);
        List<ResourceQuotaInfo> list = new ArrayList<>();

        client.resourceQuotas().inNamespace(namespace).list().getItems().forEach(quotas -> list.add(getResourceQuotaInfo(quotas)));

        return list;
    }

    private static ResourceQuotaInfo getResourceQuotaInfo(ResourceQuota quotas) {
        ResourceQuotaInfo info = ResourceQuotaInfo.builder()
                .name(quotas.getMetadata().getName())
                .namespace(quotas.getMetadata().getNamespace())
                .specHard(quotas.getSpec().getHard())
                .statusHard(quotas.getStatus().getHard())
                .statusUsed(quotas.getStatus().getUsed())
                .build();
        log.info("Resource Quota Info {}", info);
        return info;
    }
}
