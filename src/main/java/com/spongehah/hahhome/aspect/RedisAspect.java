package com.spongehah.hahhome.aspect;

import com.spongehah.hahhome.service.BlogService;
import com.spongehah.hahhome.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 通过AOP实现用redis更新博客浏览量
 * 可以通过aop+redis、interceptor+redis(会请求两次)、actuator(会请求两次)、纯mysql四种方式
 */
@Aspect
@Slf4j
@Component
public class RedisAspect {
    
    private final BlogService blogService;
    private final RedisUtil redisUtil;

    public RedisAspect(BlogService blogService, RedisUtil redisUtil) {
        this.blogService = blogService;
        this.redisUtil = redisUtil;
    }
    
    @Pointcut("execution(* com.spongehah.hahhome.service.impl.BlogServiceImpl.getBlogById(..))")
    private void myPt(){}
    
    @After("myPt()")
    public void doAfter(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];
        //根据id更新浏览量
        redisUtil.zIncrementScore("views",id.toString(),1);
    }
}
