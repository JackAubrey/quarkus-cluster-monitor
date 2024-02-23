package com.quarkus.developers.mappers;

import io.fabric8.kubernetes.api.model.Quantity;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Map;

@Mapper(componentModel = "jakarta", uses = QuantityMapper.class)
public interface QuotaMapMapper {
    Map<String, BigDecimal> map(Map<String, Quantity> idEmployeeMap);
}
