package com.quarkus.developers.util;

import io.smallrye.common.constraint.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public abstract class AssertUtil {
    /**
     *
     */
    AssertUtil() {
        super();
    }

    public static void hasText(String string, @NotNull String errorMessage) {
        if( StringUtils.isBlank(string) ) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void notNull(Object object, @NotNull String errorMessage) {
        if( Objects.isNull(object) ) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
