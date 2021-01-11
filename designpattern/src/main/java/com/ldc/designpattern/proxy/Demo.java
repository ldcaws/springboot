package com.ldc.designpattern.proxy;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:09
 */
public class Demo {

    public static void main(String[] args) {
        // 这里用代理类来实例化
        FoodService foodService = new FoodServiceProxy();
        foodService.makeChicken();
    }

}
