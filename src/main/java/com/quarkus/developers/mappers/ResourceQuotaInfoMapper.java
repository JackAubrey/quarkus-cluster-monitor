package com.quarkus.developers.mappers;

import com.quarkus.developers.data.ResourceQuotaInfo;
import io.fabric8.kubernetes.api.model.ResourceQuota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jakarta", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = QuotaMapMapper.class)
public interface ResourceQuotaInfoMapper {
    @Mapping(target = "name", source = "metadata.name")
    @Mapping(target = "namespace", source = "metadata.namespace")
    @Mapping(target = "specHard", source = "spec.hard")
    @Mapping(target = "statusHard", source = "status.hard")
    @Mapping(target = "statusUsed", source = "status.used")
    ResourceQuotaInfo toSimpleQuotaInfo(ResourceQuota quota);
}
