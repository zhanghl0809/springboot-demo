package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.common.annotations.PassToken;
import com.example.demo.common.annotations.UseToken;
import com.example.demo.common.result.ApiResult;
import com.example.demo.domin.TbUser;
import com.example.demo.request.dto.LoginDto;
import com.example.demo.request.dto.UserAddDto;
import com.example.demo.service.UserService;
import com.example.demo.service.design.strategy.TestStrategyFactory;
import com.example.demo.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private TestStrategyFactory factory;

    @UseToken
    @PassToken
    @GetMapping("/1")
    public ApiResult<Object> getAllUser(@RequestParam("id") String id) throws Exception {
        List<TbUser> users = userService.selectAllUser();
        redisUtil.set(id, JSON.toJSONString(users));
        return ApiResult.SUCCESS(users);
    }


    @UseToken
    @PostMapping("/2")
    public ApiResult<Object> postGet(@RequestBody LoginDto loginDto) {
        List<TbUser> users = userService.selectAllUser();
        return ApiResult.SUCCESS(users);
    }

    @GetMapping("/3")
    public ApiResult<Object> testStrategy(@RequestParam("id") int id) throws Exception {
        String s = factory.getHandler(id).doSomeThing(11111);
        return ApiResult.SUCCESS(s);
    }


    @PostMapping("/addUser")
    public ApiResult<Object> addUser(@RequestBody UserAddDto userAddDto) {
        TbUser user = new TbUser();
        BeanUtils.copyProperties(userAddDto, user);
        return ApiResult.SUCCESS(userService.addUser(user));
    }

    @PassToken
    @GetMapping("/lock")
    public ApiResult<Object> lock(@RequestParam("id") String id) {

        redisUtil.set("redis01", "哈哈哈哈哈");
        RBucket<String> key = redissonClient.getBucket("redisson01");
        key.set("嘿嘿嘿嘿嘿");

        // 获取字符串格式的数据
        RBucket<String> keyObj = redissonClient.getBucket("redis01");
        String data = keyObj.get();

        RBucket<String> keyObj1= redissonClient.getBucket("redisson01");
        String data1 = keyObj1.get();

//        RLock lock = redissonClient.getLock(id);
//        try {
//            lock.lock();
//        } catch (Exception e) {
//            throw new BizException(BizResultEnum.SELECT_USER_ERROR,"加锁失败");
//        } finally {
//            lock.unlock();
//        }
        return ApiResult.SUCCESS(data+"--"+data1);
    }


// 签到系统、秒杀系统
//id生成、过滤器、token秘钥验证、aop拦截器、自定义注解\、mq、redis、代码生成、nacos、XXL-JOb

    public static void main(String[] args) {
        TbUser user = new TbUser();
        boolean aNull = Objects.isNull(user);
        System.out.println(aNull);
    }
}
/**
 *
 * SETBIT key offset value
 * - value oloy 0 or 1
 * bitmap有自己的一套命令。可以把bitmap想象成一个以bit为单位的数组，数组的每个单元存储0和1，数组的下标叫做偏移量。
 * 使用场景一：用户签到
 * 使用场景二：统计活跃用户
 * 使用场景三：用户在线状态
 */


