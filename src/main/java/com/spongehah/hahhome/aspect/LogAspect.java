package com.spongehah.hahhome.aspect;

import com.spongehah.hahhome.pojo.ReturningLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取controller方法的请求信息和返回值输出到日志文件
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    /**
     * 获取请求信息和返回值输出到日志
     */
    @Pointcut("execution(* com.spongehah.hahhome.controller.*.*(..))")
    private void logPointcut(){}
    
    @Before("logPointcut()")
    public void BeforeLog(JoinPoint joinpoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName();
        Object[] args = joinpoint.getArgs();
        ReturningLog returningLog = new ReturningLog(url,ip,classMethod,args);
        log.info("请求信息:{}", returningLog);
    }
    
    @AfterReturning(pointcut = "logPointcut()",returning = "obj")
    public void afterReturning(Object obj){
        log.info("返回值:{}", obj);
    }
}
