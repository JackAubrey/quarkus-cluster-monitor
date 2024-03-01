package com.quarkus.developers.mappers;

import com.quarkus.developers.dtos.ServiceDto;
import com.quarkus.developers.mappers.decoders.IntOrStringDecoder;
import com.quarkus.developers.mappers.decoders.PodIPDecoder;
import com.quarkus.developers.mappers.decoders.QuantityDecoder;
import io.fabric8.kubernetes.api.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jakarta", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {QuantityDecoder.class, PodIPDecoder.class, IntOrStringDecoder.class})
public interface ServiceDtoMapper {
    @Mapping(target = "metadata.name", source = "metadata.name")
    @Mapping(target = "metadata.namespace", source = "metadata.namespace")
    @Mapping(target = "metadata.creationTimestamp", source = "metadata.creationTimestamp")
    @Mapping(target = "metadata.deletionTimestamp", source = "metadata.deletionTimestamp")
    @Mapping(target = "metadata.deletionGracePeriodSeconds", source = "metadata.deletionGracePeriodSeconds")
    @Mapping(target = "metadata.generateName", source = "metadata.generateName")
    @Mapping(target = "metadata.annotations", source = "metadata.annotations")
    @Mapping(target = "metadata.labels", source = "metadata.labels")
    @Mapping(target = "spec.clusterIPs", source = "spec.clusterIPs")
    @Mapping(target = "spec.ports", source = "spec.ports")
    @Mapping(target = "status.conditions", source = "status.conditions")
    ServiceDto toDto(Service service);
}
