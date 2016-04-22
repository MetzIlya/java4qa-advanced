package com.db.edu.chat.server;

import com.db.edu.chat.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static org.junit.Assume.assumeNotNull;

public class JUnitLoadTest {
    private static final Logger logger = LoggerFactory.getLogger(JUnitLoadTest.class);
    Server server;
    private IOException gotException = null;

    @Before
    public void setUp() throws ServerError, IOException {
        server = new Server();
        server.start();

//        sleep(300);
    }

    @Test(timeout = 1000)
    public void shouldGetMessageBackWhenSendMessage() throws IOException, InterruptedException {

        final String sentMessage = Thread.currentThread().getName() + ";seed:" + Math.random();
        logger.debug("Sending message: " + sentMessage);

        Socket readerClientSocket = null;
        try {
            readerClientSocket = new Socket(Configuration.HOST, Configuration.PORT);
        } catch (IOException e) {
            logger.error("Can't connect to server: ", e);
        }
        assumeNotNull(readerClientSocket);

        final BufferedReader readerClientSocketReader
                = new BufferedReader(new InputStreamReader(readerClientSocket.getInputStream()));

        Thread readerClient = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String gotMessage;

                    do {
                        gotMessage = readerClientSocketReader.readLine();
                        logger.debug("Got msg: " + gotMessage);
                    } while (!sentMessage.equals(gotMessage));

                } catch (IOException e) {
                    gotException = e;
                }
            }
        });
        readerClient.start();

        final Socket writerClientSocket = new Socket(Configuration.HOST, Configuration.PORT);
        final BufferedWriter writerClientSocketWriter = new BufferedWriter(new OutputStreamWriter(writerClientSocket.getOutputStream()));
        socketWrite(writerClientSocketWriter, sentMessage);

        readerClient.join();
        if (gotException != null) throw gotException;
    }

    @After
    public void setDown() throws ServerError {
        server.stop();
    }

    // TODO: sleep should be moved to test utils
    private static void sleep(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
        }
    }

    private static void socketWrite(BufferedWriter socketWriter, String text) throws IOException {
        socketWriter.write(text);
        socketWriter.newLine();
        socketWriter.flush();
    }
}
