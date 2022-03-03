package com.example.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    @Bean("redissonClient")
    public RedissonClient redissonClient() throws IOException {
        // 本例子使用的是yaml格式的配置文件，读取使用Config.fromYAML，如果是Json文件，则使用Config.fromJSON
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader()
                .getResource("redisson-single.yml"))
                .setCodec(new StringCodec())
                .setTransportMode(TransportMode.NIO);
        return Redisson.create(config);
    }
}

