package com.quarkus.developers.mappers;

import com.quarkus.common.data.dtos.DeploymentDto;
import com.quarkus.developers.mappers.decoders.IntOrStringDecoder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jakarta", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {IntOrStringDecoder.class})
public interface DeploymentMapper {
    @Mapping(target = "metadata.name", source = "metadata.name")
    @Mapping(target = "metadata.namespace", source = "metadata.namespace")
    @Mapping(target = "metadata.creationTimestamp", source = "metadata.creationTimestamp")
    @Mapping(target = "metadata.deletionTimestamp", source = "metadata.deletionTimestamp")
    @Mapping(target = "metadata.deletionGracePeriodSeconds", source = "metadata.deletionGracePeriodSeconds")
    @Mapping(target = "metadata.generateName", source = "metadata.generateName")
    @Mapping(target = "metadata.labels", source = "metadata.labels")
    @Mapping(target = "spec.minReadySeconds", source = "spec.minReadySeconds")
    @Mapping(target = "spec.paused", source = "spec.paused")
    @Mapping(target = "spec.progressDeadlineSeconds", source = "spec.progressDeadlineSeconds")
    @Mapping(target = "spec.revisionHistoryLimit", source = "spec.revisionHistoryLimit")
    @Mapping(target = "spec.replicas", source = "spec.replicas")
    @Mapping(target = "spec.strategy.type", source = "spec.strategy.type")
    @Mapping(target = "spec.strategy.rollingUpdateMaxSurge", source = "spec.strategy.rollingUpdate.maxSurge")
    @Mapping(target = "spec.strategy.rollingUpdateMaxUnavailable", source = "spec.strategy.rollingUpdate.maxUnavailable")
    @Mapping(target = "status.availableReplicas", source = "status.availableReplicas")
    @Mapping(target = "status.collisionCount", source = "status.collisionCount")
    @Mapping(target = "status.observedGeneration", source = "status.observedGeneration")
    @Mapping(target = "status.readyReplicas", source = "status.readyReplicas")
    @Mapping(target = "status.unavailableReplicas", source = "status.unavailableReplicas")
    @Mapping(target = "status.updatedReplicas", source = "status.updatedReplicas")
    @Mapping(target = "status.conditions", source = "status.conditions")
    DeploymentDto toDto(Deployment deployment);
}
