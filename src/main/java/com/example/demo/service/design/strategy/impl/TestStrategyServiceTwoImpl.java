package com.example.demo.service.design.strategy.impl;

import com.example.demo.service.design.strategy.TestStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestStrategyServiceTwoImpl implements TestStrategyService {

    @Override
    public Integer getDataType() {
        return 2;
    }

    @Override
    public String doSomeThing(int request) {
        return "TestStrategyServiceTwoImpl---"+request;
    }
}
