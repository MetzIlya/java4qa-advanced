package com.db.edu.chat.common.processors;

public interface MessageProcessor {
    void processMessage(String message) throws MessageProcessException;
}
