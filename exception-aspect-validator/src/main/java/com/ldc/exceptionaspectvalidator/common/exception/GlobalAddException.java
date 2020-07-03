package com.ldc.exceptionaspectvalidator.common.exception;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/3 22:47
 */
public class GlobalAddException extends GlobalException {

    private static final long serialVersionUID = 1L;

    public GlobalAddException(String message) {
        super(message,401);//401为自定义
    }
}
