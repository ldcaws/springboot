package com.ldc.designpattern.factory;

import com.ldc.designpattern.factory.po.Food;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 21:12
 */
public interface FoodFactory {

    Food makeFood(String name);

}