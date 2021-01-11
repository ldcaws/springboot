package com.ldc.designpattern.builder;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 21:34
 */
public class Demo {

    public static void main(String[] args) {
        User user = User.builder()
                .name("foo")
                .password("pAss12345")
                .age(25)
                .build();
        System.out.println(user.toString());
    }

}
