package com.ldc.csrf1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ss
 * @time: 2020/5/20 14:59
 */
@RestController
public class HelloController {

    @PostMapping("/transfer")
    public void transferMoney(String name, Integer money) {
        System.out.println("name = " + name);
        System.out.println("money = " + money);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}