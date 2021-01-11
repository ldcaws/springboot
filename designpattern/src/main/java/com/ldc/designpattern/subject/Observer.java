package com.ldc.designpattern.subject;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:37
 */
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}