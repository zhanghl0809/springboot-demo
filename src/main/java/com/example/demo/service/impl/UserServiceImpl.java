package com.example.demo.service.impl;

import com.example.demo.common.enums.SnowflakeIdEnum;
import com.example.demo.domin.TbUser;
import com.example.demo.mapper.TbUserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.IdGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;


    @Override
    public List<TbUser> selectAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public TbUser selectByUserId(Long userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public int addUser(TbUser user) {
        user.setUserId(IdGenerateUtil.getLongId(SnowflakeIdEnum.USER));
        user.setCreateTime(new Date());
        return userMapper.insertSelective(user);
    }

}
