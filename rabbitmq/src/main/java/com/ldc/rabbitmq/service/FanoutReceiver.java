package com.ldc.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/16 22:02
 */
@Component
public class FanoutReceiver {

    @RabbitListener(queues = "queue-one")
    public void handler1(String msg) {
        System.out.println("FanoutReceiver-handler1：" + msg);
    }

    @RabbitListener(queues = "queue-two")
    public void handler2(String msg) {
        System.out.println("FanoutReceiver-handler2：" + msg);
    }

}
