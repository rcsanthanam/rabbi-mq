package io.ravi.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ravi.config.CommonConfig;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class MessagePublisher {
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection(CommonConfig.AMQP_URL);
        Channel channel = connection.createChannel();

        List<String> names = List.of("Ravi","Chandran","Santhanam");

        for(String name:names){
            String message = "Hello Guys "+ name;
            channel.basicPublish(
                    "",CommonConfig.DEFAULT_QUEUE,null,message.getBytes()
            );
        }

        channel.close();
        connection.close();

    }
}
