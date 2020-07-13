package com.ldc.transactionaldemo.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/13 23:01
 */
@Component
@Aspect
public class ControllerAspect {

    private static Logger log = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut("execution(* com.ldc.transactionaldemo.controller.*.*(..))")
    public void pointcut(){}

    /**
     * Description: 对所有controller方法做环切
     * @author yangbao
     * @date 2020-06-28 13:37:16
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint){
        try {
            Object processResult = joinPoint.proceed();
            return processResult;
        } catch (Throwable ex) {
            log.error(LogUtil.format("请求异常"), ex);
            throw new TliiServerException(ex.getMessage());
        }

    }
}