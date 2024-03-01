package com.quarkus.developers.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PodSpecDto {
    private Long terminationGracePeriodSeconds;
    private List<PodSpecContainerDto> containers;
}
