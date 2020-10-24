package io.ravi.subscriber;

import com.rabbitmq.client.*;
import io.ravi.config.CommonConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageSubscriber {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection(CommonConfig.AMQP_URL);
        Channel channel = connection.createChannel();

        DeliverCallback deliverCallback = (s, delivery) -> {
            System.out.println(new String(delivery.getBody()));
        };

        CancelCallback cancelCallback = s -> {
            System.out.println(s);
        };

        channel.basicConsume(CommonConfig.DEFAULT_QUEUE, true, deliverCallback, cancelCallback);
    }
}
