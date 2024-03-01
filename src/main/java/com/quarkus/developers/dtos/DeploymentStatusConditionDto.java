package com.quarkus.developers.dtos;

import lombok.Data;

@Data
public class DeploymentStatusConditionDto {
    private String status;
    private String type;
    private String lastTransitionTime;
    private String lastUpdateTime;
    private String message;
    private String reason;
}
