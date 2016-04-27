package com.db.edu.chat.common.connection;

import java.io.IOException;

public class ConnectionIOException extends IOException {
    public ConnectionIOException() {
    }

    public ConnectionIOException(String message) {
        super(message);
    }

    public ConnectionIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionIOException(Throwable cause) {
        super(cause);
    }
}
