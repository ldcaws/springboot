package com.ldc.rabbitmq.config;

import com.ldc.rabbitmq.service.HandleService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/16 22:17
 */
@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.host}")
    String host;
    @Value("${spring.rabbitmq.username}")
    String username;
    @Value("${spring.rabbitmq.password}")
    String password;
    @Value("${spring.rabbitmq.virtual-host}")
    String mqRabbitVirtualHost;

    @Value("${server.port}")
    private String port;

    private static String queueName;


    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(mqRabbitVirtualHost);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setHost(host);
        return connectionFactory;
    }

    @Bean
    public HandleService handleService() {
        return new HandleService();
    }

    //创建监听器，监听队列
    @Bean
    public SimpleMessageListenerContainer mqMessageContainer(HandleService handleService) throws AmqpException, IOException {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueueNames(withServiceId("QUEUE-WEBSOCKET"));
        container.setExposeListenerChannel(true);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);//设置确认模式为手工确认
        container.setMessageListener(handleService);//监听处理类
        return container;
    }

    //交换机
    public static final String EXCHANGE_FANOUT = "exchange_fanout";
    //队列
    //public static final String QUEUE = "queue_websocket";

    //声明交换机,交换机配置
    @Bean
    public FanoutExchange fanoutExchange_websocket() {
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    //声明队列
    @Bean
    public Queue queue_wesocket() {
        queueName = withServiceId("QUEUE-WEBSOCKET");
        return new Queue(queueName);
    }

    //绑定队列到交换机
    @Bean
    public Binding binding_websocket() {
        return BindingBuilder.bind(queue_wesocket()).to(fanoutExchange_websocket());
    }

    protected String withServiceId(String keyName) {
        String queueName = new String();
        try {
            queueName = keyName + "_" + InetAddress.getLocalHost().getHostAddress() + ":" + port;
        } catch (UnknownHostException e) {
            queueName = keyName + "_" + UUID.randomUUID();
        }
        return queueName;
    }

}

