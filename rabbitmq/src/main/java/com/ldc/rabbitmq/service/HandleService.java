package com.ldc.rabbitmq.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/16 22:31
 */
public class HandleService implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody());
        System.out.println(msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

    }
}