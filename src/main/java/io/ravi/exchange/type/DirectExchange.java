package io.ravi.exchange.type;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.ravi.config.ConnectionManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DirectExchange {

    private static final String EXCHANGE = "my-direct-exchange";

    //Declare exchange
    public static void declareExchange(){
        try(Channel channel = ConnectionManager.getConnection().createChannel()){
            //Declare direct exchange
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT,true);
        }catch (IOException | TimeoutException e){
            e.printStackTrace();
        }
    }

    //Declare Queue
    public static void declareQueue(){
        try{
            Channel channel = ConnectionManager.getConnection().createChannel();
            channel.queueDeclare("MobileQ",true,false,false,null);
            channel.queueDeclare("ACQ",true,false,false,null);
            channel.queueDeclare("LightQ",true,false,false,null);
            channel.close();
        }catch (IOException | TimeoutException e){
            e.printStackTrace();
        }
    }

    //Declare Bindings
    public static void declareBindings(){
        try{
            Channel channel = ConnectionManager.getConnection().createChannel();
            //Create bindings - (queue, exchange, routingKey)
            channel.queueBind("MobileQ",EXCHANGE,"personalDevice");
            channel.queueBind("ACQ",EXCHANGE,"homeAppliance");
            channel.queueBind("LightQ",EXCHANGE,"homeAppliance");
            channel.close();
        }catch (IOException | TimeoutException e){
            e.printStackTrace();
        }
    }

    //Create subscribers
    public static void subscribeMessage() throws IOException {
        Channel channel = ConnectionManager.getConnection().createChannel();

            channel.basicConsume("MobileQ",true,(consumerTag, message) -> {
                System.out.println(consumerTag);
                System.out.println("MobileQ "+new String(message.getBody(), StandardCharsets.UTF_8));
            }, System.out::println);

            channel.basicConsume("ACQ",true,(consumerTag, message) -> {
                System.out.println(consumerTag);
                System.out.println("ACQ "+new String(message.getBody(),StandardCharsets.UTF_8));
            }, System.out::println);

            channel.basicConsume("LightQ",true,(consumerTag, message) -> {
                System.out.println(consumerTag);
                System.out.println("LightQ "+new String(message.getBody(),StandardCharsets.UTF_8));
            }, System.out::println);

    }

    //Publish message
    public static void publishMessage(){
        try(Channel channel = ConnectionManager.getConnection().createChannel()) {
            String message = "Direct Message - Turn on the Home Appliances";
            channel.basicPublish(EXCHANGE,"homeAppliance",null,message.getBytes());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        DirectExchange.declareExchange();
        DirectExchange.declareQueue();
        DirectExchange.declareBindings();

        //Subscribe
        Thread subscribe = new Thread(() -> {
            try {
                DirectExchange.subscribeMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Publish
        Thread publish = new Thread(DirectExchange::publishMessage);

        subscribe.start();
        publish.start();
    }
}
