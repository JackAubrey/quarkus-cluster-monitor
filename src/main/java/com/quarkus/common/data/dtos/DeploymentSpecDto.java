package com.quarkus.common.data.dtos;

import lombok.Data;

@Data
public class DeploymentSpecDto {
    private Integer minReadySeconds;
    private Boolean paused;
    private Integer progressDeadlineSeconds;
    private Integer revisionHistoryLimit;
    private Integer replicas;
    private DeploymentSpecStrategyDto strategy;
}
