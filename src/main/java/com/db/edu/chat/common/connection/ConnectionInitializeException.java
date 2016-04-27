package com.db.edu.chat.common.connection;

import java.io.IOException;

/**
 * Created by Student on 27.04.2016.
 */
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
