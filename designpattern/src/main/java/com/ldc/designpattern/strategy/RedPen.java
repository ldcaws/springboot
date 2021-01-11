package com.ldc.designpattern.strategy;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:30
 */
public class RedPen implements Strategy {
    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("用红色笔画图，radius:" + radius + ", x:" + x + ", y:" + y);
    }
}