package com.webank.databrain.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.databrain.enums.CodeEnum;
import com.webank.databrain.utils.WebUtil;
import com.webank.databrain.vo.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AccessDeniedHandlerHandler implements AccessDeniedHandler {

  @Autowired private ObjectMapper objectMapper;

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    String responseStr = objectMapper.writeValueAsString(CommonResponse.error(CodeEnum.FORBIDDEN));
    log.info("response:{}", responseStr);
    WebUtil.renderString(response, responseStr);
  }
}
