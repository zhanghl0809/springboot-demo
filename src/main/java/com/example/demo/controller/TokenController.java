package com.example.demo.controller;

import com.example.demo.common.result.ApiResult;
import com.example.demo.domin.TbUser;
import com.example.demo.service.UserService;
import com.example.demo.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private UserService userService;

    @GetMapping("/getToken")
    public ApiResult<Object> getToken(@RequestParam("userId") Long userId) {
        TbUser user = userService.selectByUserId(userId);
        String token = TokenUtil.generateToken(user.getUserId());
        return ApiResult.SUCCESS(token);
    }
}
