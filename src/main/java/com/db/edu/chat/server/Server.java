package com.db.edu.chat.server;

import com.db.edu.chat.Configuration;
import com.db.edu.chat.common.Listener;
import com.db.edu.chat.common.SocketWriterProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;

public class Server {
	private static final Logger logger = LoggerFactory.getLogger(Server.class);

	//
	private final Collection<Socket> clientsSockets = new java.util.concurrent.CopyOnWriteArrayList<>();
	private volatile ServerSocket serverSocket;
	private volatile boolean stopFlag;

	// TODO: move to start method
	private Thread connectionEventLoop = new Thread() {
		@Override
		public void run() {
			while(!isInterrupted()) {
				try {
					Socket clientSocket = serverSocket.accept();
					logger.info("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

					// TODO: Should rework, to have single point for processing incoming client messages
					clientsSockets.add(clientSocket);

//					Thread clientConnectionHandler = new Thread(new ClientConnectionHandler(clientSocket, clientsSockets));
					Thread clientListener=new Thread(new Listener(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())),new SocketWriterProcessor(clientsSockets,clientSocket)));
					clientListener.setDaemon(true);
					clientListener.start();
				} catch (SocketException e) {
					logger.debug("Intentionally closed socket: time to stop");
					break;
				} catch (IOException e) {
					logger.error("Network error", e);
					break;
				}
			}
		}
	};
	
	public void start() throws ServerError {
		try {
			serverSocket = new ServerSocket(Configuration.PORT);
		} catch (IOException e) {
			throw new ServerError(e);
		}
		connectionEventLoop.start();
	}
	
	public void stop() throws ServerError {
		connectionEventLoop.interrupt();

		// TODO: WTF ?
		try { Thread.sleep(1000); } catch (InterruptedException e1) { } 
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			throw new ServerError(e);
		}
	}
	
	public static void main(String... args) throws ServerError {
		new Server().start();
	}
}
