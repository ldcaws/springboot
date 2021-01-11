package com.ldc.designpattern.template;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:45
 */
public class ConcreteTemplate extends AbstractTemplate {
    public void apply() {
        System.out.println("子类实现抽象方法 apply");
    }

    public void end() {
        System.out.println("我们可以把 method3 当做钩子方法来使用，需要的时候覆写就可以了");
    }
}