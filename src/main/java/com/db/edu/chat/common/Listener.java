package com.db.edu.chat.common;

import java.io.BufferedReader;
import java.io.IOException;

public class Listener implements Runnable {
    BufferedReader reader;
    MessageProcessor processor;

    public Listener(BufferedReader reader, MessageProcessor processor) {
        this.reader = reader;
        this.processor = processor;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String message = reader.readLine();
                if(message == null) break;

                processor.processMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
