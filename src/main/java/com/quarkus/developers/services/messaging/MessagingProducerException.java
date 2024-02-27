package com.quarkus.developers.services.messaging;

public class MessagingProducerException extends RuntimeException {
    public MessagingProducerException() {
    }

    public MessagingProducerException(String message) {
        super(message);
    }

    public MessagingProducerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessagingProducerException(Throwable cause) {
        super(cause);
    }

    public MessagingProducerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
