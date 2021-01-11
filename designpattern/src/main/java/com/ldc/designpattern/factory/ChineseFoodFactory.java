package com.ldc.designpattern.factory;

import com.ldc.designpattern.factory.po.ChineseFoodA;
import com.ldc.designpattern.factory.po.ChineseFoodB;
import com.ldc.designpattern.factory.po.Food;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 21:13
 */
public class ChineseFoodFactory implements FoodFactory {

    @Override
    public Food makeFood(String name) {
        if (name.equals("A")) {
            return new ChineseFoodA();
        } else if (name.equals("B")) {
            return new ChineseFoodB();
        } else {
            return null;
        }
    }
}