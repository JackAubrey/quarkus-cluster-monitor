package com.quarkus.developers.services;

import com.quarkus.common.data.dtos.PodDto;
import com.quarkus.common.data.dtos.ResourceQuotaDto;
import com.quarkus.common.data.dtos.ServiceDto;
import com.quarkus.developers.mappers.PodMapper;
import com.quarkus.developers.mappers.ResourceQuotaMapper;
import com.quarkus.developers.mappers.ServiceMapper;
import com.quarkus.developers.services.watching.WatchNotifier;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.ResourceQuota;
import io.fabric8.kubernetes.api.model.Service;
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
    private final ResourceQuotaMapper resourceQuotaMapper;
    private final PodMapper podMapper;
    private final ServiceMapper serviceMapper;
    private final WatchNotifier notifier;


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

    @Override
    public void notifyServices() {
        List<ServiceDto> list = listServices();
        list.forEach(notifier::notifyWatchedResource);
    }

    @Override
    public void notifyServices(String namespace) {
        List<ServiceDto> list = listServices(namespace);
        list.forEach(notifier::notifyWatchedResource);
    }

    @Override
    public List<ServiceDto> listServices() {
        return convertService(client.services().list().getItems());
    }

    @Override
    public List<ServiceDto> listServices(String namespace) {
        return convertService(client.services().inNamespace(namespace).list().getItems());
    }

    List<ServiceDto> convertService(List<Service> services) {
        if(services != null && !services.isEmpty()) {
            List<ServiceDto> list = new ArrayList<>();

            services.forEach(service -> list.add(serviceMapper.toDto(service)));

            return list;
        } else {
            return new ArrayList<>();
        }
    }

    List<ResourceQuotaDto> convertResourceQuotas(List<ResourceQuota> quotas) {
        if(quotas != null && !quotas.isEmpty()) {
            List<ResourceQuotaDto> list = new ArrayList<>();

            quotas.forEach(quota -> list.add(resourceQuotaMapper.toDto(quota)));

            return list;
        } else {
           return new ArrayList<>();
        }
    }

    List<PodDto> convertPods(List<Pod> pods) {
        if(pods != null && !pods.isEmpty()) {
            List<PodDto> list = new ArrayList<>();

            pods.forEach(pod -> list.add(podMapper.toDto(pod)));

            return list;
        } else {
            return new ArrayList<>();
        }
    }
}
