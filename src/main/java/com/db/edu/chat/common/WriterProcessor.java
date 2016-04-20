package com.db.edu.chat.common;

import java.io.BufferedWriter;
import java.io.IOException;

public class WriterProcessor implements MessageProcessor {
    BufferedWriter writer;

    public WriterProcessor(BufferedWriter writer) {
        this.writer = writer;
    }

    @Override
    public void processMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
