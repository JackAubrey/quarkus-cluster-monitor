package com.quarkus.developers.events;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class ResourceQuotaInfo {
    private String name;
    private String namespace;
    private Map<String, BigDecimal> specHard;
    private Map<String, BigDecimal> statusHard;
    private Map<String, BigDecimal> statusUsed;

}
