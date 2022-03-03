package com.example.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizResultEnum {

    /**
     * 业务错误类型
     */
    SELECT_USER_ERROR("10001", "查询用户错误。");

    /**
     * 错误码
     */
    private String code;
    /**
     * 提示信息
     */
    private String msg;
}
