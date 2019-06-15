
/**
 * testaop
 * com.chaojilaji.testaop.aop
 * <p>
 * Copyright © 2019 重庆市信息通信咨询设计院有限公司.版权所有.
 * 重庆市九龙坡区科园四路257号,400041.
 * <p>
 * 此软件未经重庆市信息通信咨询设计院有限公司许可，严禁发布、传播、使用.
 */
package com.chaojilaji.testaop.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAop {

    private static Logger logger = LoggerFactory.getLogger(TestAop.class);

    @Pointcut("execution(public * com.chaojilaji.testaop.controller.TestController.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("who: {}", joinPoint.getSignature().getDeclaringTypeName() + " " + joinPoint.getSignature().getName());
        //
    }

    @After("log()")
    public void doAfter() {

    }

    @AfterReturning(returning = "obj", pointcut = "log()")
    public void doAfterReturning(Object obj) {
        logger.info("result: {}", obj);
    }

}