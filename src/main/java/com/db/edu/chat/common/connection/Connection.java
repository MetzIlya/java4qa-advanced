package com.db.edu.chat.common.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

public class Connection {
    private Socket ownSocket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private static final Logger logger = LoggerFactory.getLogger(Connection.class);

    public Socket getOwnSocket() {
        return ownSocket;
    }

    public boolean isOwn(Connection connection){
        return this.ownSocket==connection.getOwnSocket();
    }

    public Connection(Socket socket) throws ConnectionInitializeException {
        this.ownSocket = socket;
        try {
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            logger.error("Socket opening exception ",e);
            throw new ConnectionInitializeException("Socket opening exception ",e);
        }
    }
    public String read() throws ConnectionIOException{
        try {
            return reader.readLine();
        } catch (IOException e) {
            logger.error("Read exception ",e);
            throw new ConnectionIOException("Read exception ",e);
        }
    }
    public void write(String message)throws ConnectionIOException, SocketException{
        if (ownSocket.isClosed()) return;
        if (!ownSocket.isBound()) return;
        if (!ownSocket.isConnected()) return;
        logger.debug("Writing message {} to socket {}", message,ownSocket.getInetAddress()+":"+ownSocket.getPort());
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (SocketException e){
            throw e;
        }catch (IOException e) {
            logger.error("Write exception ",e);
            throw new ConnectionIOException("Write exception ",e);
        }

    }
}
