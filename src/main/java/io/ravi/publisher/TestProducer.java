package io.ravi.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ravi.config.CommonConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TestProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection(CommonConfig.AMQP_URL);
        Channel channel = connection.createChannel();
        //Basic publish
        String message = "Turn on home appliances";
        // publish with (exchange, routingKey, properties, messageBody)
        channel.basicPublish("my-direct-exchange", "homeAppliance", null, message.getBytes());
        //Close the channel and connection
        channel.close();
        connection.close();
    }
}
