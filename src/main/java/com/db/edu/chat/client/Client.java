package com.db.edu.chat.client;

import com.db.edu.chat.Configuration;
import com.db.edu.chat.common.Listener;
import com.db.edu.chat.common.WriterProcessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	public static void main(String... args) throws IOException {

		final Socket socket = new Socket(Configuration.HOST, Configuration.PORT);
		final BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		final BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter consoleWriter = new BufferedWriter(new OutputStreamWriter(System.out));


		Thread consoleListener =new Thread(new Listener(consoleReader,new WriterProcessor(socketWriter)));
		consoleListener.start();
		Thread socketListener=new Thread(new Listener(socketReader,new WriterProcessor(consoleWriter)));
		socketListener.start();
	}
}
