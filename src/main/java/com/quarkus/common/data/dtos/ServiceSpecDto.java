package com.quarkus.common.data.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ServiceSpecDto {
    private List<String> clusterIPs;
    private List<ServiceSpecPortDto> ports;
}
