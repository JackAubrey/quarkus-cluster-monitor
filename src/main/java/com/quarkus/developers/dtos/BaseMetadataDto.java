package com.quarkus.developers.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class BaseMetadataDto {
    private String name;
    private String namespace;
    private String creationTimestamp;
    private String deletionTimestamp;
    private Long deletionGracePeriodSeconds;
    private String generateName;
    private Map<String, String> annotations;
    private Map<String, String> labels;
}
