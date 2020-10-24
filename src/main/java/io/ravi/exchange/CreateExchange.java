package io.ravi.exchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ravi.config.CommonConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CreateExchange {
    public static void main(String[] args)  {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection(CommonConfig.AMQP_URL);
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare("my-direct-exchange", BuiltinExchangeType.DIRECT, true);
        } catch (IOException | TimeoutException e) {
            System.out.println("Exception "+e.getMessage());
        }

    }
}
