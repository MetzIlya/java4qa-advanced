package com.db.edu.chat.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by Student on 22.04.2016.
 */
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

    public Connection(Socket socket) {
        this.ownSocket = socket;
        try {
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String read(){
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void write(String message){
        if (ownSocket.isClosed()) return;
        if (!ownSocket.isBound()) return;
        if (!ownSocket.isConnected()) return;
        logger.debug("Writing message {} to socket {}", message,ownSocket.getInetAddress()+":"+ownSocket.getPort());
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
