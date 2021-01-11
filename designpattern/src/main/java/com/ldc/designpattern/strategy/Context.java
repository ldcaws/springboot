package com.ldc.designpattern.strategy;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/11 22:31
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeDraw(int radius, int x, int y){
        strategy.draw(radius, x, y);
    }
}