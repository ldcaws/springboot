package com.ldc.exceptionaspectvalidator.common.exception;

import com.ldc.exceptionaspectvalidator.common.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/3 22:57
 */
//@ControllerAdvice//未返回自定义的对象
@RestControllerAdvice//返回自定义的对象
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(GlobalException.class)//其执行顺序在切面类后
    public Object globalException(HttpServletRequest request, GlobalException e) {
        logger.error("系统异常：请求URL：" + request.getRequestURL() + "出现错误！");
        printStackTrace(e);
        return ResponseData.fail(e.getMessage(), e.getCode());
    }

    /**
     * 打印异常堆栈信息
     *
     * @param ex
     */
    private void printStackTrace(Exception ex) {
        logger.error("打印异常信息");
        StringBuilder errors = new StringBuilder();
        errors.append("【异常信息】\r\n");
        errors.append(ex.getClass().getName());
        if (ex.getMessage() != null) {
            errors.append(": ");
            errors.append(ex.getMessage());
        }
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            errors.append("\r\n\tat ");
            errors.append(stackTraceElement.toString());
        }
        //打印异常堆栈信息
        logger.error(errors.toString());
    }

}
