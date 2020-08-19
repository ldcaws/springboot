package com.ldc.designpattern.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: ss
 * @time: 2020/8/18 22:26
 */
@Service
public class OrderService {

    @Autowired
    private ApplicationContext applicationContext;

    public void saveOrder() {
        // todo:订单入库
        System.out.println("1、订单创建成功");

        // 创建了一个订单事件
        OrderEvent orderEvent = new OrderEvent("参数");
        // 发布事件给spring容器
        applicationContext.publishEvent(orderEvent);
        // 监听

        // todo:扩展
        //System.out.println("2、短信发送成功");

    }
}
