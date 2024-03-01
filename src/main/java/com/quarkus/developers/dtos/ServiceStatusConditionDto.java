package com.quarkus.developers.dtos;

import lombok.Data;

@Data
public class ServiceStatusConditionDto {
    private String status;
    private String type;
    private String lastTransitionTime;
    private String message;
    private String reason;
}
