package com.quarkus.developers.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ServiceStatusDto {
    private List<ServiceStatusConditionDto> conditions;
}
