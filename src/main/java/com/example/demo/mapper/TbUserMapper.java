package com.example.demo.mapper;

import com.example.demo.domin.TbUser;
import com.example.demo.mapper.base.MyBatisBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TbUserMapper继承基类
 */
@Mapper
public interface TbUserMapper extends MyBatisBaseMapper<TbUser, Integer> {

    List<TbUser> selectAllUser();

    TbUser selectByUserId(Long userId);
}