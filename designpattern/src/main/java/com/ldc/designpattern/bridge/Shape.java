package com.ldc.designpattern.bridge;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:17
 */
public abstract class Shape {
    protected DrawAPI drawAPI;
    protected Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }
    public abstract void draw();
}