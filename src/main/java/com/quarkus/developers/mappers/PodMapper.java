package com.quarkus.developers.mappers;

import com.quarkus.common.data.dtos.PodDto;
import com.quarkus.developers.mappers.decoders.PodIPDecoder;
import com.quarkus.developers.mappers.decoders.QuantityDecoder;
import io.fabric8.kubernetes.api.model.Pod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jakarta", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {QuantityDecoder.class, PodIPDecoder.class})
public interface PodMapper {
    @Mapping(target = "metadata.name", source = "metadata.name")
    @Mapping(target = "metadata.namespace", source = "metadata.namespace")
    @Mapping(target = "metadata.creationTimestamp", source = "metadata.creationTimestamp")
    @Mapping(target = "metadata.deletionTimestamp", source = "metadata.deletionTimestamp")
    @Mapping(target = "metadata.deletionGracePeriodSeconds", source = "metadata.deletionGracePeriodSeconds")
    @Mapping(target = "metadata.generateName", source = "metadata.generateName")
    @Mapping(target = "metadata.labels", source = "metadata.labels")
    @Mapping(target = "spec.terminationGracePeriodSeconds", source = "spec.terminationGracePeriodSeconds")
    @Mapping(target = "spec.containers", source = "spec.containers")
    @Mapping(target = "status.phase", source = "status.phase")
    @Mapping(target = "status.startTime", source = "status.startTime")
    @Mapping(target = "status.podIPs", source = "status.podIPs")
    @Mapping(target = "status.conditions", source = "status.conditions")
    PodDto toDto(Pod pod);
}
