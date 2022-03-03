package com.example.demo.domin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_user
 * @author 
 */
@Data
public class TbUser implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 描述
     */
    private String message;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}