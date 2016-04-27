package com.db.edu.chat.common.processors;

import com.db.edu.chat.common.connection.Connection;
import com.db.edu.chat.common.connection.ConnectionIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.util.Collection;

public class SocketWriterProcessor implements MessageProcessor{
    Collection<Connection> connections;
    Connection own;
    private static final Logger logger = LoggerFactory.getLogger(SocketWriterProcessor.class);

    public SocketWriterProcessor(Collection<Connection> connections, Connection own) {
        this.connections = connections;
        this.own = own;
    }

    @Override
    public void processMessage(String message) throws MessageProcessException {
        logger.debug("Processing {} to a collection of Connections {}",message,connections);
        for (Connection connection : connections) {
                if(!own.isOwn(connection)) {
                    try {
                        connection.write(message);
                    } catch (SocketException e){
                        connections.remove(connection);
                    } catch(ConnectionIOException e) {
                        throw new MessageProcessException(e);
                    }
                }
        }

    }

}
