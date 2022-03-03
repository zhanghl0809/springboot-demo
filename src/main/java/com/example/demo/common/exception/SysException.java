package com.example.demo.common.exception;

import com.example.demo.common.enums.ResultEnum;
import lombok.Data;

/**
 * 系统异常
 */
@Data
public class SysException extends RuntimeException{

    private String code;

    /**
     * 使用已有的错误类型
     * @param type 枚举类中的错误类型
     */
    public SysException(ResultEnum type){
        super(type.getMessage());
        this.code = type.getCode();
    }

    /**
     * 自定义错误类型
     * @param type 枚举类中的错误类型
     * @param msg 自定义补充错误提示
     */
    public SysException(ResultEnum type, String msg){
        super(type.getMessage() + msg);
        this.code =type.getCode();
    }
}
