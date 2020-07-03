package com.ldc.exceptionaspectvalidator.common.exception;

import java.io.Serializable;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/3 22:19
 */
public class GlobalException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
