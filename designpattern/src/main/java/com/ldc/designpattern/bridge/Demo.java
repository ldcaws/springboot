package com.ldc.designpattern.bridge;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:18
 */
public class Demo {

    public static void main(String[] args) {
        Shape greenCircle = new Circle(10, new GreenPen());
        Shape redRectangle = new Rectangle(4, 8, new RedPen());
        greenCircle.draw();
        redRectangle.draw();
    }

}
