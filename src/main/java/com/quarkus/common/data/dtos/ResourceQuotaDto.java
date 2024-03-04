package com.quarkus.common.data.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceQuotaDto extends BaseDto {
    private ResourceQuotaSpecDto spec;
    private ResourceQuotaStatusDto status;
}
