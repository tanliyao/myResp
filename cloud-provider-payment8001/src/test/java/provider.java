import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author tly
 * @date 2021/6/13 15:09
 */
public class provider {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 5; i++) {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("47.96.28.143");
                factory.setPort(5672);
                factory.setVirtualHost("/ems");
                factory.setUsername("userems");
                factory.setPassword("123");
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                channel.queueDeclare("hello", false, false, false, null);
                channel.basicPublish("", "hello", null, "hello rabbitmq".getBytes());
                channel.close();
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
