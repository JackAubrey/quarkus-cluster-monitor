package com.quarkus.common.data.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
public class ClusterResourceEvent {
    private String producer;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime created;
    private String payloadType;
    private Map<String, Object> payloadContent;
}
