package com.ldc.designpattern.factory;

import com.ldc.designpattern.factory.po.Food;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 21:25
 */
public class Demo {

    public static void main(String[] args) {
        // 先选择一个具体的工厂
        FoodFactory factory = new ChineseFoodFactory();
        // 由第一步的工厂产生具体的对象，不同的工厂造出不一样的对象
        Food food = factory.makeFood("A");
    }

}
