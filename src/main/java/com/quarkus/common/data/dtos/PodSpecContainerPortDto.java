package com.quarkus.common.data.dtos;

import lombok.Data;

@Data
public class PodSpecContainerPortDto {
    private Integer containerPort;
    private String protocol;
    private String name;
}
