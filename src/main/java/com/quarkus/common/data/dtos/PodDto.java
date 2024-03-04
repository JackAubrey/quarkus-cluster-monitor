package com.quarkus.common.data.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PodDto extends BaseDto {
    private PodSpecDto spec;
    private PodStatusDto status;
}
