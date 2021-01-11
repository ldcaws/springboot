package com.ldc.designpattern.demo;

import java.util.concurrent.locks.LockSupport;

/**
 * @description:
 * @author: ss
 * @time: 2020/10/20 20:42
 */
public class HashMapDemo {

    public static void main(String[] args) {

        LockSupport.unpark(Thread.currentThread());

        LockSupport.park();

        System.out.println("hello");

        LockSupport.unpark(Thread.currentThread());

        LockSupport.unpark(Thread.currentThread());

        LockSupport.park();

        LockSupport.park();

        System.out.println("hello");

    }
    
}