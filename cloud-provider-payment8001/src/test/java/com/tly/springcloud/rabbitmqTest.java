package com.tly.springcloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author tly
 * @date 2021/6/10 15:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class rabbitmqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void rabbitmq() throws IOException, TimeoutException {
        rabbitTemplate.convertAndSend("hello", "hello direct");
    }

    @Test
    public void work(){
        rabbitTemplate.convertAndSend("work", "work send");
    }

    @Test
    public void fanout(){
        rabbitTemplate.convertAndSend("fanouts","","fanout模型");
    }

    @Test
    public void route(){
        rabbitTemplate.convertAndSend("route-direct", "warn","route-direct模型");
    }
}
