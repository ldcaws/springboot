package com.ldc.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/16 21:39
 */
@Component
public class DirectReceiver {

    @RabbitListener(queues = "queueName")
    public void handler(String msg) {
        System.out.println("DirectReceiver:" + msg);
    }
}
