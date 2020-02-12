package com.tydic.signaltest.utils;

import lombok.Data;

/**
 * @Author superxiong
 * @Date 2020/2/11 16:55
 * @Version 1.0
 * 返回值封装类
 */
@Data
public class ResponseResult<T> {
    private static final int CODE_SUCCESS = 1;//成功状态码
    private static final int CODE_FAILURE = 0;//失败状态码
    private int code;//状态码
    private T data;//返回数据
    private String msg;//返回信息

    public ResponseResult() {
        super();
    }

    /**
     * @param code
     * @param data
     * @param msg
     */
    public ResponseResult(int code, T data, String msg) {
        super();
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    public ResponseResult<T> getSuccess(T data) {
        return new ResponseResult<T>(CODE_SUCCESS, data, "success");
    }

    public ResponseResult<T> getSuccess(T data, String msg) {
        return new ResponseResult<T>(CODE_SUCCESS, data, msg);
    }

    public ResponseResult<T> getFailure(String msg) {
        return new ResponseResult<T>(CODE_FAILURE, null, msg);
    }
}
