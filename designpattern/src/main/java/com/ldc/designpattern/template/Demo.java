package com.ldc.designpattern.template;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:45
 */
public class Demo {

    public static void main(String[] args) {
        AbstractTemplate t = new ConcreteTemplate();
        // 调用模板方法
        t.templateMethod();
    }

}
