package com.example.demo.common.enums;

/**
 * 统一返回信息枚举
 * @author zhanghl
 */

public enum ResultEnum {

    SUCCESS("00000", "请求成功。"),
    FAIL("11111", "业务处理失败。"),
    IP_LIMIT("88888","请勿多次请求。"),
    EXCEPTION("99999", "系统异常。"),
    ERROR("-1", "服务器正忙，请稍后再试。");



    private String code;
    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }

}
