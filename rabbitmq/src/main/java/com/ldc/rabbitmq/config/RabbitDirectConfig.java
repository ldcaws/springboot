package com.ldc.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @description:
 * @author: ss
 * @time: 2020/6/16 21:28
 */
@Configuration
public class RabbitDirectConfig {

    public final static String DIRECTNAME = "directName";

    @Bean
    Queue queue() {
        return new Queue("queueName");
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(DIRECTNAME, true, false);
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with("direct");
    }

}
