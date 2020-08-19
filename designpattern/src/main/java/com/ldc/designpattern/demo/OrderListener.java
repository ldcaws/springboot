package com.ldc.designpattern.demo;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ss
 * @time: 2020/8/18 22:40
 */
@Component
public class OrderListener implements ApplicationListener<OrderEvent> {

    @Override
    public void onApplicationEvent(OrderEvent orderEvent) {
        //todo:发送短信
        System.out.println("2、发送短信成功");
    }

}
