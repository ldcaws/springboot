package com.ldc.designpattern.sale;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: ss
 * @time: 2020/8/18 23:18
 */
@Service
public class NormalStrategy implements CalculateStrategy {

    @Override
    public String userType() {
        return "normal";
    }

    @Override
    public double discount(double fee) {
        return fee*1.0;
    }

}
