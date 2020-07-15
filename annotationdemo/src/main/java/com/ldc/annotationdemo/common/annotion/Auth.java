package com.ldc.annotationdemo.common.annotion;

import java.lang.annotation.*;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/15 21:45
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    //是否需要验证，true为校验，false为不校验
    //自定义注解+拦截器 实现登录校验
    boolean value() default true;
}
