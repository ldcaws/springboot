package com.ldc.annotationdemo.common.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/15 22:18
 */
public class CheckTimeIntervalValidator implements ConstraintValidator<CheckTimeInterval, Object> {

    private String startTime;
    private String endTime;

    @Override
    public void initialize(CheckTimeInterval constraintAnnotation) {
        this.startTime = constraintAnnotation.startTime();
        this.endTime = constraintAnnotation.endTime();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (null == o) return true;
        BeanWrapper beanWrapper = new BeanWrapperImpl(o);
        Object start = beanWrapper.getPropertyValue(startTime);
        Object end = beanWrapper.getPropertyValue(endTime);
        if (null == start || null == end) return true;
        int result = ((Date) end).compareTo((Date) start);
        if (result >= 0) return true;
        return false;
    }
}
