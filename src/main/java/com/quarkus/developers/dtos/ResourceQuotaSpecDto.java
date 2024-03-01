package com.quarkus.developers.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ResourceQuotaSpecDto {
    private List<String> scopes;
    private Map<String, BigDecimal> hard;
}
