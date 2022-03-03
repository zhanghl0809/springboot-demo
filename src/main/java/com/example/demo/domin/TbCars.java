package com.example.demo.domin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_cars
 * @author 
 */
@Data
public class TbCars implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 汽车名称
     */
    private String name;

    /**
     * 型号
     */
    private String xh;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 体积
     */
    private Integer tiji;

}