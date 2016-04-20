package com.db.edu.chat.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;

public class SocketWriterProcessor implements MessageProcessor{
    Collection<Socket> sockets;
    Socket mySocket;
    private static final Logger logger = LoggerFactory.getLogger(SocketWriterProcessor.class);
    public SocketWriterProcessor(Collection<Socket> sockets, Socket mySocket) {
        this.sockets = sockets;
        this.mySocket=mySocket;
    }

    @Override
    public void processMessage(String message) {
        for (Socket outSocket : sockets) {
            try {
                // TODO: Can use single if
                if (outSocket.isClosed()) continue;
                if (!outSocket.isBound()) continue;
                if (!outSocket.isConnected()) continue;
                if (outSocket == this.mySocket) continue;
                // TODO: Doesn't update list when client is unreachable
                logger.info("Writing message " + message + " to socket " + outSocket);

                BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(outSocket.getOutputStream()));
                socketWriter.write(message);
                socketWriter.newLine();
                socketWriter.flush();
            } catch (IOException e) {
                logger.error("Error writing message " + message + " to socket " + outSocket + ". Closing socket", e);
                try {
                    outSocket.close();
                } catch (IOException innerE) {
                    logger.error("Error closing socket ", innerE);
                }

                logger.error("Removing connection " + outSocket);
                sockets.remove(outSocket);
            }
        }
        
    }
}
