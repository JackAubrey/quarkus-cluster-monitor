package com.quarkus.developers.controllers;

import com.quarkus.common.data.dtos.PodDto;
import com.quarkus.common.data.dtos.ResourceQuotaDto;
import com.quarkus.developers.services.MonitorFacade;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.openshift.api.model.Project;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Path("/monitor")
public class MonitorResource {
    private final MonitorFacade monitorFacade;

    @GET()
    @Path("/namespaces")
    public Response listNamespaces() {
        List<Namespace> list = monitorFacade.listNamespaces();
        return Response.ok(list, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET()
    @Path("/projects")
    public Response listProjects() {
        List<Project> list = monitorFacade.listProjects();
        return Response.ok(list, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET()
    @Path("/pods")
    public Response listPod() {
        List<PodDto> list = monitorFacade.listPods();
        return Response.ok(list, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET()
    @Path("/pods/${namespace}")
    public Response listPod(@PathParam("namespace")String namespace) {
        List<PodDto> list = monitorFacade.listPods(namespace);
        return Response.ok(list, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET()
    @Path("/resource-quotas")
    public Response listResourceQuotas() {
        List<ResourceQuotaDto> list = monitorFacade.listResourceQuotas();
        return Response.ok(list, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET()
    @Path("/resource-quotas/${namespace}")
    public Response listResourceQuotas(@PathParam("namespace") String namespace) {
        List<ResourceQuotaDto> list = monitorFacade.listResourceQuotas(namespace);
        return Response.ok(list, MediaType.APPLICATION_JSON_TYPE).build();
    }
}
