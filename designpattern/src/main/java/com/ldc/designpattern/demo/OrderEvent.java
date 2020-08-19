package com.ldc.designpattern.demo;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ss
 * @time: 2020/8/18 22:36
 */
public class OrderEvent extends ApplicationEvent {
    public OrderEvent(Object source) {// 当我们事件发布
        super(source);
    }
}
