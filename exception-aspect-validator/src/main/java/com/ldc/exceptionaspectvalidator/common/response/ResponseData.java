package com.ldc.exceptionaspectvalidator.common.response;

/**
 * @description:统一的接口返回格式类
 * @author: ss
 * @time: 2020/7/3 23:28
 */
public class ResponseData<T> {

    private Boolean status = true; //状态
    private Integer code = 200; //响应吗
    private String message;//消息
    private T data;//返回的数据

    public ResponseData() {
        super();
    }

    public ResponseData(T data) {
        super();
        this.data = data;
    }

    public ResponseData(T data,String message) {
        super();
        this.data = data;
        this.message=message;
    }

    public static ResponseData success(Object data) {
        return new ResponseData(data);
    }

    public static ResponseData success(Object data,String message) {
        return new ResponseData(data,message);
    }

    public static ResponseData fail(String message) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus(false);
        responseData.setCode(400);
        responseData.setMessage(message);
        responseData.setData(null);
        return responseData;
    }

    public static ResponseData fail(String message,int code) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus(false);
        responseData.setCode(code);
        responseData.setMessage(message);
        responseData.setData(null);
        return responseData;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
