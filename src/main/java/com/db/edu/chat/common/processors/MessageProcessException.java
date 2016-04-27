package com.db.edu.chat.common.processors;

public class MessageProcessException extends Exception {
    public MessageProcessException() {
    }

    public MessageProcessException(String message) {
        super(message);
    }

    public MessageProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageProcessException(Throwable cause) {
        super(cause);
    }

    public MessageProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
