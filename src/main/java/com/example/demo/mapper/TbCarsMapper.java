package com.example.demo.mapper;

import com.example.demo.domin.TbCars;
import com.example.demo.mapper.base.MyBatisBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TbCarsMapper继承基类
 */
@Mapper
public interface TbCarsMapper extends MyBatisBaseMapper<TbCars, Integer> {
}