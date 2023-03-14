package com.webank.databrain.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.databrain.vo.common.CommonRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class WebLogAspect {
    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("execution(public * com.webank.databrain.controller..*.*(..))")
    public void webLog() { }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws JsonProcessingException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object[] params = joinPoint.getArgs();
        HttpServletRequest request = attributes.getRequest();
        CommonRequest req = (CommonRequest)params[0];
        log.info("request : {}",objectMapper.writeValueAsString(req));
        log.info("from={} path={} method={} classMethod={}", request.getRemoteAddr(), request.getRequestURI(), request.getMethod(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        long executeTime = System.currentTimeMillis() - beginTime;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = signature.getName();
        log.info("spend time : {}", executeTime);
        return result;
    }

    @AfterReturning(value = "webLog()", returning = "o")
    public void doAfter(Object o) throws JsonProcessingException {
        log.info("response : {}", objectMapper.writeValueAsString(o));
    }
}
