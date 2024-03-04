package com.quarkus.common.data.dtos;

import lombok.Data;

@Data
public class DeploymentSpecStrategyDto {
    private String type;
    private String rollingUpdateMaxSurge;
    private String rollingUpdateMaxUnavailable;
}
