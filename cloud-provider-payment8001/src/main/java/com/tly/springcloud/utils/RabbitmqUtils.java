package com.tly.springcloud.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author tly
 * @date 2021/6/13 15:34
 */
public class RabbitmqUtils {
    private static ConnectionFactory factory;

    static {
        factory = new ConnectionFactory();
        factory.setHost("47.96.28.143");
        factory.setPort(5672);
        factory.setVirtualHost("/ems");
        factory.setUsername("userems");
        factory.setPassword("123");
    }

    public static Connection getConnection() {
        try {
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(Channel channel, Connection connection) {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

