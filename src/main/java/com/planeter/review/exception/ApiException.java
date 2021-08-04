package com.planeter.review.exception;


import com.planeter.review.enums.ResultCode;
import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class ApiException extends RuntimeException {
    private final String msg;
    private final ResultCode resultCode;

    public ApiException() {
        this(ResultCode.FAILED);
    }

    public ApiException(String msg) {
        this(ResultCode.FAILED, msg);
    }

    public ApiException(ResultCode resultCode) {
        this(resultCode, resultCode.getMsg());
    }

    public ApiException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
        this.msg =  msg;
    }
}
