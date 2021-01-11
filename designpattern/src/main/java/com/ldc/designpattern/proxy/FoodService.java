package com.ldc.designpattern.proxy;

import com.ldc.designpattern.factory.po.Food;

public interface FoodService {
    Food makeChicken();
    Food makeNoodle();
}