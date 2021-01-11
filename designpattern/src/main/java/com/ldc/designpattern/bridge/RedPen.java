package com.ldc.designpattern.bridge;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:15
 */
public class RedPen implements DrawAPI {
    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("用红色笔画图，radius:" + radius + ", x:" + x + ", y:" + y);
    }
}