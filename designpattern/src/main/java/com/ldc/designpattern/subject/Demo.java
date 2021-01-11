package com.ldc.designpattern.subject;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:39
 */
public class Demo {

    public static void main(String[] args) {
        // 先定义一个主题
        Subject subject = new Subject();
        // 定义观察者
        new BinaryObserver(subject);
        new HexaObserver(subject);
        // 模拟数据变更，这个时候，观察者们的 update 方法将会被调用
        subject.setState(11);
    }

}
