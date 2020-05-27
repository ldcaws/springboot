package com.ldc.quartzdemo.config;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description:
 * @author: ss
 * @time: 2020/5/27 22:29
 */
@Component
public class FirstJob {
    public void sayHello() {
        System.out.println("FirstJob:sayHello:" + new Date());
    }
}
