package com.ldc.annotationdemo.common.aop;

import com.ldc.annotationdemo.common.annotion.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/15 22:38
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //@Pointcut("execution(* com.richsoft.*.*(..)) && !@annotation(com.richsoft.common.log.DisableSystemLog)")
    //切入点签名的方法，注意返回值必须是void,相当于切入点的无参构造
    @Pointcut("@annotation(com.ldc.annotationdemo.common.annotion.Log)")
    public void controllerPointCut() {
    }

    //	前置增强
    @Before("controllerPointCut()")
    public void LogBefore(JoinPoint jp) {
        logger.info("【日志记录】用户（最好记录当前用户）调用了【" + jp.getTarget().getClass().getSimpleName() +
                "】的【" + jp.getSignature().getName() + "】的方法，方法入参为【"
                + Arrays.toString(jp.getArgs()) + "】");
        // 接收到请求，记录请求内容(这里同样可以在前置增强配置请求的相关信息)
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("请求的地址URL : " + request.getRequestURL().toString());
        logger.info("请求的方式HTTP_METHOD : " + request.getMethod());
        logger.info("请求的IP : " + request.getRemoteAddr());
        logger.info("请求的全类名 : " + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
        logger.info("请求的参数(数组形式) : " + Arrays.toString(jp.getArgs()));

        try {
            request.setAttribute("eventType",getEventType(jp));
            request.setAttribute("operationType",getOperationType(jp));
            request.setAttribute("eventLevel",getEventLevel(jp));
            request.setAttribute("content",getContent(jp));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //后置增强
    @AfterReturning(pointcut = "controllerPointCut()", returning = "result")
    public void controllerAfterReturing(JoinPoint jp, Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("【日志记录】用户（最好记录当前用户）调用了【" + jp.getTarget().getClass().getSimpleName() +
                "】的【" + jp.getSignature().getName() + "】的方法，方法返回值【" + result + "】");
        try {

            //入库
            getEventType(jp);
            getOperationType(jp);
            getEventLevel(jp);
            getContent(jp);
            System.out.println(getEventType(jp) + getOperationType(jp) + getEventLevel(jp) + getContent(jp));

        } catch (Exception e) {
            logger.error("插入日志出现异常：", e);
        }

    }

    //	异常抛出增强
    @AfterThrowing(pointcut = "controllerPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint jp, RuntimeException e) {
        logger.error("【日志记录】用户（最好记录当前用户）调用了【" + jp.getTarget().getClass().getSimpleName() + "】的【" + jp.getSignature().getName() + "】方法发生异常【" + e + "】");
    }

    //环绕增强
    @Around("controllerPointCut()")
    public Object aroundLogger(ProceedingJoinPoint jp) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = jp.proceed();
            logger.info("【日志记录】用户（最好记录当前用户）调用了【" + jp.getTarget().getClass().getSimpleName() +
                    "】的【" + jp.getSignature().getName() + "】的方法耗时：" + (System.currentTimeMillis() - startTime) + "毫秒。");
            return result;
        } catch (Throwable e) {
            throw e;
        } finally {
            logger.info("【日志记录】用户（最好记录当前用户）调用了【" + jp.getTarget().getClass().getSimpleName() +
                    "】的【" + jp.getSignature().getName() + "】的方法结束执行。");
        }
    }

    /**
     * 获取日志内容
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private String getContent(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(Log.class).content();
                    break;
                }
            }
        }
        return description;
    }

    private String getEventType(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String eventType = "0";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    eventType = method.getAnnotation(Log.class).eventType();
                    break;
                }
            }
        }
        return eventType;
    }

    /**
     * 获取操作类型
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private String getOperationType(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String operationType = "0";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    operationType = method.getAnnotation(Log.class).operationType();
                    break;
                }
            }
        }
        return operationType;
    }

    private String getEventLevel(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String eventLevel = "0";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    eventLevel = method.getAnnotation(Log.class).eventLevel();
                    break;
                }
            }
        }
        return eventLevel;
    }
}
