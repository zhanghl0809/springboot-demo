package com.example.demo.common.enums;

public enum TokenEnum {

    /**
     * token错误类型
     */
    TOKEN_INVALID("77701","token无效。"),
    TOKEN_VALID("77702","token正确。"),
    TOKEN_ERROR("77703","token验证错误。"),
    TOKEN_NULL("77777","请先获取token。");

    /**
     * 错误码
     */
    private String code;
    /**
     * 提示信息
     */
    private String message;

    TokenEnum(String code, String message) {
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
