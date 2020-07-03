package com.ldc.exceptionaspectvalidator.common.exception;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/3 22:35
 */
public class GlobalParamException extends GlobalException {

    private static final long serialVersionUID = 1L;

    public GlobalParamException(String message) {
        super(message,400);//400为自定义
    }
}
