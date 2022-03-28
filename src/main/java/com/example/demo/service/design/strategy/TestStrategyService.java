package com.example.demo.service.design.strategy;

public interface TestStrategyService {

    /**
     * 返回处理数据的类型
     *
     * @return int
     */
    Integer getDataType();

    /**
     * 做些些事
     * @param request
     * @return
     */
    String doSomeThing(int request);

}
