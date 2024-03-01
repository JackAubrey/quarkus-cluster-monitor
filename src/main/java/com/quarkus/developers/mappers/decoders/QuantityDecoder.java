package com.quarkus.developers.mappers.decoders;

import io.fabric8.kubernetes.api.model.Quantity;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;

@ApplicationScoped
public class QuantityDecoder {
    public BigDecimal asBigDecimal(Quantity quantity) {
        return quantity == null ? null : quantity.getNumericalAmount();
    }
}
