package com.db.edu.chat.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class Listener implements Runnable {
    BufferedReader reader;
    MessageProcessor processor;
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    public Listener(BufferedReader reader, MessageProcessor processor) {
        this.reader = reader;
        this.processor = processor;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String message = reader.readLine();
                logger.debug("Message received - {}",message);
                if(message == null) break;

                processor.processMessage(message);
            } catch (IOException e) {
                logger.error("",e);
            }
        }

    }
}
