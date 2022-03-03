package com.example.demo.service;

import com.example.demo.domin.TbUser;

import java.util.List;

public interface UserService {

    List<TbUser> selectAllUser();

    int addUser(TbUser user);

    TbUser selectByUserId(Long userId);
}
