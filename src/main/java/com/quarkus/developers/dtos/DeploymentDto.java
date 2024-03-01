package com.quarkus.developers.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeploymentDto extends BaseDto {
    private DeploymentSpecDto spec;
    private DeploymentStatusDto status;
}
