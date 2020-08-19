package com.ldc.designpattern.sale;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: ss
 * @time: 2020/8/18 23:19
 */
@Service
public class VipStrategy implements CalculateStrategy {

    @Override
    public String userType() {
        return "vip";
    }

    @Override
    public double discount(double fee) {
        return fee*0.8;
    }

}
