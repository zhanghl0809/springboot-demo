package com.example.demo.common.result;

import com.example.demo.common.enums.ResultEnum;
import lombok.Data;

/**
 * 统一返回
 * @author zhanghl
 * @param <T>
 */
@Data
public class ApiResult<T> {

    private String code;
    private String message;
    private T data;

    public ApiResult() {
    }

    public ApiResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功
     */
    public static <T> ApiResult<T> SUCCESS(T data) {
        ApiResult<T> result = new ApiResult<T>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 成功
     */
    public static <T> ApiResult<T> SUCCESS() {
        ApiResult<T> result = new ApiResult<T>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        result.setData(null);
        return result;
    }


    /**
     * 失败
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> FAIL(String message) {
        ApiResult<T> result = new ApiResult<T>();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMessage(ResultEnum.FAIL.getMessage() + message);
        result.setData(null);
        return result;
    }

    /**
     * 异常
     */
    public static <T> ApiResult<T> EXCEPTION(String code, String message) {
        return new ApiResult(code, message);
    }

}
