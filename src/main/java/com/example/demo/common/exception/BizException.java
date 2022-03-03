package com.example.demo.common.exception;

import com.example.demo.common.enums.BizResultEnum;
import lombok.Data;

/**
 * 业务异常
 */
@Data
public class BizException extends RuntimeException{

    private String code;

    /**
     * 使用已有的错误类型
     * @param type 枚举类中的错误类型
     */
    public BizException(BizResultEnum type){
        super(type.getMsg());
        this.code = type.getCode();
    }

    /**
     * 自定义错误类型
     * @param type 枚举类中的错误类型
     * @param msg 自定义补充错误提示
     */
    public BizException(BizResultEnum type, String msg){
        super(type.getMsg() + msg);
        this.code =type.getCode();
    }
}
