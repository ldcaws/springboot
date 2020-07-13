package com.ldc.transactionaldemo.common;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/13 22:58
 */
public class TliiServerException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;

    public TliiServerException(String message) {
        super(message);
    }
}
