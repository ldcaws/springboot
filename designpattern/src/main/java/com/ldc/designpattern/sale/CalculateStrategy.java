package com.ldc.designpattern.sale;

public interface CalculateStrategy {

    public String userType();

    public double discount(double fee);
}
