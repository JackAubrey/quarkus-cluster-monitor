package com.quarkus.common.data.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PodSpecDto {
    private Long terminationGracePeriodSeconds;
    private List<PodSpecContainerDto> containers;
}
