package com.quarkus.developers.exceptions;

public class WatchNotifierException extends RuntimeException {
    public WatchNotifierException(String message, Throwable cause) {
        super(message, cause);
    }
}
