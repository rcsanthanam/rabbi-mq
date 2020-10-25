package io.ravi.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionManager {

    private ConnectionManager() throws IllegalAccessException {
        throw new IllegalAccessException("Can't create Instance");
    }

    private static volatile Connection INSTANCE = null;

    public static Connection getConnection(){
        Connection localRef = INSTANCE;
        if(localRef == null){
            synchronized (ConnectionManager.class){
                localRef = INSTANCE;
                if(localRef == null){
                    ConnectionFactory factory = new ConnectionFactory();
                    try {
                        localRef = INSTANCE = factory.newConnection(CommonConfig.AMQP_URL);
                    } catch (IOException  | TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return localRef;
    }
}
