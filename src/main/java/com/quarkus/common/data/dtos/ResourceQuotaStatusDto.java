package com.quarkus.common.data.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ResourceQuotaStatusDto {
    private Map<String, BigDecimal> hard;
    private Map<String, BigDecimal> used;
}
