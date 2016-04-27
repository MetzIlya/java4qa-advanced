package com.db.edu.chat.client;

import com.db.edu.chat.Configuration;
import com.db.edu.chat.common.Listener;
import com.db.edu.chat.common.processors.WriterProcessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	private Client(){}
	public static void main(String... args) {

		final Socket socket;
		try {
			socket = new Socket(Configuration.HOST, Configuration.PORT);
		} catch (IOException e) {
			System.out.println("Error opening socket!"+e.toString());
			return;
		}
		final BufferedWriter socketWriter;
		final BufferedReader socketReader;
		try {
			socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error getting IO streams from Socket!"+e.toString());
			return;
		}
		final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		final BufferedWriter consoleWriter = new BufferedWriter(new OutputStreamWriter(System.out));


		Thread consoleListener =new Thread(new Listener(consoleReader,new WriterProcessor(socketWriter)));
		consoleListener.start();
		Thread socketListener=new Thread(new Listener(socketReader,new WriterProcessor(consoleWriter)));
		socketListener.start();
	}
}
