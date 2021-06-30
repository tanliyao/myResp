package com.tly.springcloud.utils;

import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

/**
 * @author tly
 * @date 2021/6/13 15:36
 */

public class Rabbitmq {

    @Autowired
    private static RabbitTemplate rabbitTemplate;

    public static void main(String[] args) throws IOException, TimeoutException {
//        Rabbitmq.direct();

//        Rabbitmq.workConsumer1();
//        Rabbitmq.workConsumer2();
//        Rabbitmq.work();

//        Rabbitmq.fanoutConsumer1();
//        Rabbitmq.fanoutConsumer2();
//        Rabbitmq.fanout();

//        Rabbitmq.routingDirectConsumer1();
//        Rabbitmq.routingDirectConsumer2();
//        Rabbitmq.routingDirect();

//        Rabbitmq.routingTopicConsumer1();
//        Rabbitmq.rountingTopic();

        springbootRabbitmq();
    }

    public static void springbootRabbitmq(){
        rabbitTemplate.convertAndSend("hello", "springboot direct hello");
    }

    public static void direct() throws IOException, TimeoutException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", false, false, false, null);
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rabbitmq".getBytes());
        RabbitmqUtils.closeConnection(channel, connection);

        Connection connection1 = RabbitmqUtils.getConnection();
        Channel channel1 = connection1.createChannel();
        channel1.basicConsume("hello", new DefaultConsumer(channel1) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消息 " + new String(body));
            }
        });
    }

    public static void work() throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", false, false, false, null);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rabbitmq".getBytes());
        }
        RabbitmqUtils.closeConnection(channel, connection);
    }

    public static void workConsumer1() throws IOException {
        Connection connection1 = RabbitmqUtils.getConnection();
        Channel channel1 = connection1.createChannel();
        channel1.basicConsume("hello", true, new DefaultConsumer(channel1) {
            int i = 0;

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body) + "  " + (++i));
            }
        });
    }

    public static void workConsumer2() throws IOException {

        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(2);
        channel.basicConsume("hello", false, new DefaultConsumer(channel) {
            int i = 0;

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2: " + new String(body) + " " + (++i));
//                Thread.sleep("2000");
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }

    public static void fanoutConsumer1() throws IOException {
        Connection connection1 = RabbitmqUtils.getConnection();
        Channel channel1 = connection1.createChannel();
        String queue = channel1.queueDeclare().getQueue();
        channel1.queueBind(queue, "logs", "");
        channel1.basicConsume(queue, true, new DefaultConsumer(channel1) {
            int i = 0;

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body) + " " + (++i));
            }
        });
    }

    public static void fanoutConsumer2() throws IOException {
        Connection connection1 = RabbitmqUtils.getConnection();
        Channel channel1 = connection1.createChannel();
        String queue = channel1.queueDeclare().getQueue();
        channel1.queueBind(queue, "logs", "");
        channel1.basicConsume(queue, true, new DefaultConsumer(channel1) {
            int i = 0;

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2: " + new String(body) + " " + (++i));
            }
        });
    }

    public static void fanout() throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs", "fanout");
        channel.basicPublish("logs", "", null, "fanout type message".getBytes());
        RabbitmqUtils.closeConnection(channel, connection);
    }

    public static void routingDirect() throws IOException {
        String routingkey = "info";
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("log_direct", "direct");
        channel.basicPublish("log_direct", routingkey, null, (" 这是routing-direct模型:[" + routingkey + "]").getBytes());
        RabbitmqUtils.closeConnection(channel, connection);
    }

    public static void routingDirectConsumer1() throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("log_direct", "direct");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "log_direct", "info");
        channel.basicConsume(queue, new DefaultConsumer(channel) {
            int i = 0;

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body) + " " + (++i));
            }
        });
    }

    public static void routingDirectConsumer2() throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("log_direct", "direct");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "log_direct", "info");
        channel.queueBind(queue, "log_direct", "warn");
        channel.queueBind(queue, "log_direct", "error");
        channel.basicConsume(queue, new DefaultConsumer(channel) {
            int i = 0;

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2: " + new String(body) + " " + (++i));
            }
        });
    }

    public static void rountingTopic() throws IOException {
        String routingKey = "user.save";

        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("topics", "topic");
        channel.basicPublish("topics", routingKey, null, ("routing-topic " + routingKey).getBytes());
        RabbitmqUtils.closeConnection(channel, connection);
    }

    public static void routingTopicConsumer1() throws IOException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("topics", "topic");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "topics", "*.save");
        channel.basicConsume(queue, new DefaultConsumer(channel) {
            int i = 0;

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body) + " " + (++i));
            }
        });
    }
}
