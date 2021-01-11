package com.ldc.designpattern.bridge;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:18
 */
public class Circle extends Shape {
    private int radius;
    public Circle(int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.radius = radius;
    }
    public void draw() {
        drawAPI.draw(radius, 0, 0);
    }
}