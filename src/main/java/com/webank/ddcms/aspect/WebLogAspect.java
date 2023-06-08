package com.webank.ddcms.aspect;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.ddcms.vo.common.CommonRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class WebLogAspect extends ResponseEntityExceptionHandler {
  @Autowired private ObjectMapper objectMapper;

  @Pointcut("execution(public * com.webank.ddcms.controller..*.*(..))")
  public void webLog() {}

  @Before("webLog()")
  public void doBefore(JoinPoint joinPoint) throws JsonProcessingException {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    Object[] params = joinPoint.getArgs();
    log.info(
        "from={} path={} method={} classMethod={}",
        request.getRemoteAddr(),
        request.getRequestURI(),
        request.getMethod(),
        joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    if (!StringUtils.equalsIgnoreCase(request.getMethod(), "GET")
        && params.length > 0
        && !(params[0] instanceof CommonRequest)) {
      String contentType = request.getContentType();
      if (StringUtils.containsIgnoreCase(contentType, "application/x-www-form-urlencoded")
          || StringUtils.containsIgnoreCase(contentType, "multipart/form-data")) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String paramsStr = objectMapper.writeValueAsString(parameterMap);
        log.info("Parameters : {}", paramsStr);
        for (Object param : params) {
          if (param instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) param;
            log.info(
                "Filename : {}, size : {}, contentType : {}",
                file.getOriginalFilename(),
                file.getSize(),
                file.getContentType());
          }
        }
      }
    } else if (params.length > 0 && params[0] instanceof CommonRequest) {
      CommonRequest req = (CommonRequest) params[0];
      log.info("request : {}", objectMapper.writeValueAsString(req));
    } else {
      log.info("Request : {}", request.getMethod());
    }
  }

  @Around("webLog()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    long beginTime = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    long executeTime = System.currentTimeMillis() - beginTime;
    log.info("spend time : {}", executeTime);
    return result;
  }

  @AfterReturning(value = "webLog()", returning = "o")
  public void doAfter(Object o) throws IOException {
    log.info("response : {}", JSONUtil.toJsonPrettyStr(o));
  }
}
