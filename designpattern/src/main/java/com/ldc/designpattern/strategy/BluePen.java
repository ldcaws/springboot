package com.ldc.designpattern.strategy;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:30
 */
public class BluePen implements Strategy {
    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("用蓝色笔画图，radius:" + radius + ", x:" + x + ", y:" + y);
    }
}