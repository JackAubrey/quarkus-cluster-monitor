package com.quarkus.developers.mappers;

import com.quarkus.common.data.dtos.ResourceQuotaDto;
import com.quarkus.developers.mappers.decoders.QuantityDecoder;
import io.fabric8.kubernetes.api.model.ResourceQuota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jakarta", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {QuantityDecoder.class})
public interface ResourceQuotaDtoMapper {
    @Mapping(target = "metadata.name", source = "metadata.name")
    @Mapping(target = "metadata.namespace", source = "metadata.namespace")
    @Mapping(target = "metadata.creationTimestamp", source = "metadata.creationTimestamp")
    @Mapping(target = "metadata.deletionTimestamp", source = "metadata.deletionTimestamp")
    @Mapping(target = "metadata.deletionGracePeriodSeconds", source = "metadata.deletionGracePeriodSeconds")
    @Mapping(target = "metadata.generateName", source = "metadata.generateName")
    @Mapping(target = "metadata.labels", source = "metadata.labels")
    @Mapping(target = "spec.hard", source = "spec.hard")
    @Mapping(target = "spec.scopes", source = "spec.scopes")
    @Mapping(target = "status.hard", source = "status.hard")
    @Mapping(target = "status.used", source = "status.used")
    ResourceQuotaDto toDto(ResourceQuota quota);
}
