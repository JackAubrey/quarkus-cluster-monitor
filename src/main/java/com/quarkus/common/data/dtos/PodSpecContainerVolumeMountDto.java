package com.quarkus.common.data.dtos;

import lombok.Data;

@Data
public class PodSpecContainerVolumeMountDto {
    private String mountPath;
    private Boolean readOnly;
}
