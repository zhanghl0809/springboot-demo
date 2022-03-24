package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * https://mp.weixin.qq.com/s/Kw4P9s_8lYg6Nqw8yRiVCw
 */
@Component
public class RedisUtil {
    @Autowired
    RedisTemplate redisTemplate;   //key-value是对象的

    //判断是否存在key
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public boolean deleteKey(String key){
        return redisTemplate.delete(key);
    }

    //从redis中获取值
    public  Object get(String key){
        return  redisTemplate.opsForValue().get(key);
    }

    //向redis插入值
    public  boolean set(final String key,Object value){
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    public Long setIncr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long setIncrValue(String key, long delta) {
        return redisTemplate.opsForValue().increment(key,delta);
    }
}
