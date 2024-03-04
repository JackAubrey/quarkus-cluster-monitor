package com.quarkus.common.data.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class PodSpecContainerDto {
    private String image;
    private String imagePullPolicy;
    private String name;

    private List<PodSpecContainerPortDto> ports;
    private List<PodSpecContainerVolumeMountDto> volumeMounts;
    private Map<String, BigDecimal> limits;
    private Map<String, BigDecimal> requests;
}
