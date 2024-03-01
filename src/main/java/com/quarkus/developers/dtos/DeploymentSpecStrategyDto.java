package com.quarkus.developers.dtos;

import lombok.Data;

@Data
public class DeploymentSpecStrategyDto {
    private String type;
    private String rollingUpdateMaxSurge;
    private String rollingUpdateMaxUnavailable;
}
