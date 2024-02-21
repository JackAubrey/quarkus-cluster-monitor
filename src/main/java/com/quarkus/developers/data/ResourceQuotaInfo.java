package com.quarkus.developers.data;

import io.fabric8.kubernetes.api.model.Quantity;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResourceQuotaInfo {
    private String name;
    private String namespace;
    private Map<String, Quantity> specHard;
    private Map<String, Quantity> statusHard;
    private Map<String, Quantity> statusUsed;

}
