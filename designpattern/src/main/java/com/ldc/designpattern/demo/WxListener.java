package com.ldc.designpattern.demo;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ss
 * @time: 2020/8/18 22:50
 */
@Component
public class WxListener implements ApplicationListener<OrderEvent> {

    @Override
    public void onApplicationEvent(OrderEvent orderEvent) {
        // todo:发送微信成功
        System.out.println("3、发送微信成功");
    }

}
