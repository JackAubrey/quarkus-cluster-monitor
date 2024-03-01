package com.quarkus.developers.mappers.decoders;

import io.fabric8.kubernetes.api.model.IntOrString;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IntOrStringDecoder {
    public String asString(IntOrString intOrString) {
        return intOrString == null ? null : intOrString.getStrVal();
    }
}
