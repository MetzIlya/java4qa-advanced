package com.db.edu.chat.common.connection;

import java.io.IOException;

public class ConnectionInitializeException extends IOException {
    public ConnectionInitializeException() {
    }

    public ConnectionInitializeException(String message) {
        super(message);
    }

    public ConnectionInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionInitializeException(Throwable cause) {
        super(cause);
    }
}
