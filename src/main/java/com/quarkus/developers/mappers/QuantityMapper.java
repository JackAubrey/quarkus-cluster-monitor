package com.quarkus.developers.mappers;

import io.fabric8.kubernetes.api.model.Quantity;
import jakarta.enterprise.context.Dependent;

import java.math.BigDecimal;

@Dependent
public class QuantityMapper {
    public BigDecimal asBigDecimal(Quantity quantity) {
        return quantity.getNumericalAmount();
    }
}
