package com.ldc.exceptionaspectvalidator.common.exception;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/3 22:49
 */
public class GlobalQueryException extends GlobalException {

    private static final long serialVersionUID = 1L;

    public GlobalQueryException(String message) {
        super(message,402);//402为自定义
    }

}
