package com.db.edu.chat.server;

import com.db.edu.chat.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ChatServerLoadApp {
    private static final Logger logger = LoggerFactory.getLogger(ChatServerLoadApp.class);

	public static void main(String... args) throws IOException {
		while(true) {
			ChatServerLoadApp.sleep(1);
            final Socket socket = new Socket(Configuration.HOST, Configuration.PORT);

			new Thread() {
				@Override
				public void run() {
					try {
						BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						String message = Thread.currentThread().getName();
						
						while(true) {
							ChatServerLoadApp.sleep(1);
							socketWrite(socketWriter, message);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}.start();
			
			new Thread() {
				@Override
				public void run() {
					try {
						BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						while(true) {
							// TODO: Print to console quite heavy operation, will affect test results
							System.out.println( ">>>>" + socketReader.readLine() );
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
	private static void socketWrite(BufferedWriter socketWriter, String text) throws IOException {
		socketWriter.write(text);
		socketWriter.newLine();
		socketWriter.flush();
	}

	// TODO: Rename sleep to sleepSeconds,
	// make more common and move to TestUtils
	private static void sleep(int seconds) {
		try { Thread.sleep(1000*seconds); } catch (InterruptedException e) { }
	}
}
