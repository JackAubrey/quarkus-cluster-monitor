package com.quarkus.developers.dtos;

import lombok.Data;

@Data
public class PodSpecContainerVolumeMountDto {
    private String mountPath;
    private Boolean readOnly;
}
