package com.tly.springcloud.utils;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author tly
 * @date 2021/6/16 0:02
 */
@Component
public class springbootRabbitmq {

    @RabbitListener(queues = "hello")
    public void receive(String message){
        System.out.println("springboot direct hello =="+message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void workReceive1(String message){
        System.out.println("springboot work1 hello =="+message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void workReceive2(String message){
        System.out.println("springboot work2 hello =="+message);
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue,//创建临时队列
            exchange = @Exchange(value = "fanouts",type = "fanout")//绑定的交换机
    )})
    public void fanoutReceive1(String message){
        System.out.println("springboot fanout1 hello =="+message);
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue,//创建临时队列
            exchange = @Exchange(value = "fanouts",type = "fanout")//绑定的交换机
    )})
    public void fanoutReceive2(String message){
        System.out.println("springboot fanout2 hello =="+message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value ="route-direct"),
            key = {"info","warn","error"})
    )
    public void routeDirect1(String message){
        System.out.println("springboot route-direct1 == "+message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value ="route-direct"),
            key = {"info"})
    )
    public void routeDirect2(String message){
        System.out.println("springboot route-direct2 == "+message);
    }
}
