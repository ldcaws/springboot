package com.ldc.designpattern.strategy;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:32
 */
public class Demo {

    public static void main(String[] args) {
        Context context = new Context(new BluePen()); // 使用绿色笔来画
        context.executeDraw(10, 0, 0);
    }

}
