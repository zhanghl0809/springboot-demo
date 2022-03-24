package com.example.demo.common.exception;

import com.example.demo.common.enums.ResultEnum;
import com.example.demo.common.enums.TokenEnum;
import lombok.Data;

/**
 * IP限制异常
 */
@Data
public class IpException extends RuntimeException{
    private String code;

    /**
     * 使用已有的错误类型
     * @param type 枚举类中的错误类型
     */
    public IpException(ResultEnum type){
        super(type.getMessage());
        this.code = type.getCode();
    }

    /**
     * 自定义错误类型
     * @param type 枚举类中的错误类型
     * @param msg 自定义补充错误提示
     */
    public IpException(ResultEnum type, String msg){
        super(type.getMessage() + msg);
        this.code =type.getCode();
    }

    public IpException(String code, String msg){
        super( msg);
        this.code =code;
    }
}
