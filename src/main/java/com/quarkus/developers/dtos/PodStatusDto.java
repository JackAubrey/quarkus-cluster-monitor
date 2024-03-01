package com.quarkus.developers.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PodStatusDto {
    private String phase;
    private String startTime;
    private List<String> podIPs;
    private List<PodStatusConditionDto> conditions;
}
