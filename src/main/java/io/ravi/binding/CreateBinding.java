package io.ravi.binding;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ravi.config.CommonConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CreateBinding {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection(CommonConfig.AMQP_URL);

        try(Channel channel = connection.createChannel()){
            channel.queueBind("MobileQ","my-direct-exchange","personalDevice");
            channel.queueBind("ACQ","my-direct-exchange","homeAppliance");
            channel.queueBind("LightQ","my-direct-exchange","homeAppliance");
        }

        connection.close();
    }
}
