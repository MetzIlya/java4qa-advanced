package com.db.edu.chat.common.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;

public class WriterProcessor implements MessageProcessor {
    BufferedWriter writer;
    private static final Logger logger = LoggerFactory.getLogger(SocketWriterProcessor.class);

    public WriterProcessor(BufferedWriter writer) {
        this.writer = writer;
    }

    @Override
    public void processMessage(String message) throws MessageProcessException {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            logger.error("IO Exception",e);
            throw new MessageProcessException("IO Exception",e);
        }

    }
}
