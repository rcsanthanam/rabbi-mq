package io.ravi.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ravi.config.CommonConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CreateQueue {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection(CommonConfig.AMQP_URL);
        Channel channel = connection.createChannel();

        channel.queueDeclare("MobileQ",true,false,true,null);
        channel.queueDeclare("ACQ",true,false,true,null);
        channel.queueDeclare("LightQ",true,false,true,null);

        channel.close();
        connection.close();
    }
}
