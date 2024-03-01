package com.quarkus.developers.dtos;

import lombok.Data;

@Data
public class ServiceSpecPortDto {
    private String name;
    private Integer port;
    private Integer targetPort;
    private String protocol;
}
