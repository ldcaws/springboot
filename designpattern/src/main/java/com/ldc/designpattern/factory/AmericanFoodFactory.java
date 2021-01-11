package com.ldc.designpattern.factory;

import com.ldc.designpattern.factory.po.AmericanFoodA;
import com.ldc.designpattern.factory.po.AmericanFoodB;
import com.ldc.designpattern.factory.po.Food;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 21:24
 */
public class AmericanFoodFactory implements FoodFactory {

    @Override
    public Food makeFood(String name) {
        if (name.equals("A")) {
            return new AmericanFoodA();
        } else if (name.equals("B")) {
            return new AmericanFoodB();
        } else {
            return null;
        }
    }
}