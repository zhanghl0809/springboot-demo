package com.example.demo.service.design.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TestStrategyFactory implements ApplicationContextAware ,InitializingBean {

    private ApplicationContext appContext;
    private static final Map<Integer, TestStrategyService> SERVICE_MAP = new HashMap<>();

    public TestStrategyService getHandler(Integer dataType) {
        return SERVICE_MAP.get(dataType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[TestStrategyFactory]-开始注册服务");
        appContext.getBeansOfType(TestStrategyService.class)
                .values()
                .forEach(service -> SERVICE_MAP.put(service.getDataType(), service));
        log.info("[TestStrategyFactory]-完成注册服务");
    }
}
