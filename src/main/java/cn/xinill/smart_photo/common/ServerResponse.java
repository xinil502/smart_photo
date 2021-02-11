package cn.xinill.smart_photo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候，如果是null对象，key也会消失
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    //成功返回
    public static <T>ServerResponse<T> createBySuccess(){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createBySuccessData(T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccessMsgData(String msg,T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    //失败返回
    public static <T> ServerResponse<T> createByErrorMsg(String errorMessage) {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMsg(int code, String errorMessage) {
        return new ServerResponse<>(code,errorMessage);
    }
    public static <T> ServerResponse<T> createByErrorMsgData(String errorMessage, T Data) {
        return new ServerResponse<>(1,errorMessage, Data);
    }
    public static <T> ServerResponse<T> createByErrorCodeMsgData(int code, String errorMessage, T Data) {
        return new ServerResponse<>(code, errorMessage, Data);
    }
}