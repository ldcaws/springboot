package com.ldc.designpattern.proxy;

import com.ldc.designpattern.factory.po.Chicken;
import com.ldc.designpattern.factory.po.Food;
import com.ldc.designpattern.factory.po.Noodle;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 21:49
 */
public class FoodServiceImpl implements FoodService {
    public Food makeChicken() {
        Chicken f = new Chicken();
        f.setChicken("1kg");
        f.setSpicy("1g");
        f.setSalt("3g");
        return f;
    }
    public Food makeNoodle() {
        Noodle f = new Noodle();
        f.setNoodle("500g");
        f.setSalt("5g");
        return f;
    }
}