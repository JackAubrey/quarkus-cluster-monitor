package com.quarkus.common.data.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceDto extends BaseDto {
    private ServiceSpecDto spec;
    private ServiceStatusDto status;
}
