package com.example.demo.common.aspect;

import com.example.demo.common.enums.ResultEnum;
import com.example.demo.common.exception.SysException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Aspect
@Configuration
@Slf4j
public class SysAspect {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 定义切点 切点为controller下所有的类
     * 其中类里的所有方法为连接点
     */
    @Pointcut("execution(* com.example.demo.controller..*.*(..))")
    public void sysAspect() {
    }

    /**
     * 环绕通知
     */
    @Around(value = "sysAspect()")
    public Object webLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        String className = joinPoint.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = className + "." + signature.getMethod().getName() + "():";
        log.info("==========> {} 方法请求报文:{}", methodName, objectMapper.writeValueAsString(joinPoint.getArgs()));
        proceed = joinPoint.proceed();
        log.info("==========> {} 方法响应报文:{}", methodName, objectMapper.writeValueAsString(proceed));
        return proceed;
    }
}
