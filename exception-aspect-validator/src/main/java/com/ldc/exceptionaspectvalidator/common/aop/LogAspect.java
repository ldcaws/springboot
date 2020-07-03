package com.ldc.exceptionaspectvalidator.common.aop;

import com.ldc.exceptionaspectvalidator.common.exception.GlobalParamException;
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
 * @time: 2020/7/3 23:10
 */
@Component
@Aspect
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.ldc.exceptionaspectvalidator.*.*.*(..))")//注意路径，直到类所在的上一级，.*.*(..)是固定
    public void controllerPointCut() {
    }

    //环切
    @Around("controllerPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws GlobalParamException {
        long startTime = System.currentTimeMillis();
        try {
            Object processResult = proceedingJoinPoint.proceed();
            logger.info("【日志记录】用户（最好记录当前用户）调用了【" +
                    proceedingJoinPoint.getTarget().getClass().getSimpleName() +
                    "】的【" +
                    proceedingJoinPoint.getSignature().getName() +
                    "】的方法耗时：" +
                    (System.currentTimeMillis() - startTime)+"毫秒。");
            return processResult;
        } catch (Throwable ex) {
            logger.error("请求异常");
            //若抛出rumtimeexception，则是抛出系统返回，非自定义；抛出自定义则被按格式统一抛出
            throw new GlobalParamException(ex.getMessage());
        }
    }

}
