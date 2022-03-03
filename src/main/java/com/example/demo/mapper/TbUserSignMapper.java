package com.example.demo.mapper;

import com.example.demo.domin.TbUserSign;
import com.example.demo.mapper.base.MyBatisBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TbUserSignMapper继承基类
 */
@Mapper
public interface TbUserSignMapper extends MyBatisBaseMapper<TbUserSign, Integer> {
}