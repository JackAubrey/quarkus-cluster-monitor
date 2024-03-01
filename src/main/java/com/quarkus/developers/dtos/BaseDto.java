package com.quarkus.developers.dtos;

import lombok.Data;

@Data
public class BaseDto {
    private String actionName;
    private BaseMetadataDto metadata;
}
