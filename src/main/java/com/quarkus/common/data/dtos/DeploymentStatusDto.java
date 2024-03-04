package com.quarkus.common.data.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DeploymentStatusDto {
    private Integer availableReplicas;
    private Integer collisionCount;
    private Long observedGeneration;
    private Integer readyReplicas;
    private Integer unavailableReplicas;
    private Integer updatedReplicas;
    private List<DeploymentStatusConditionDto> conditions;
}
