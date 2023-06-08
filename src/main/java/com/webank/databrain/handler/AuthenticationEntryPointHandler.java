package com.webank.databrain.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.databrain.enums.CodeEnum;
import com.webank.databrain.utils.WebUtil;
import com.webank.databrain.vo.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

  @Autowired private ObjectMapper objectMapper;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    String responseStr =
        objectMapper.writeValueAsString(CommonResponse.error(CodeEnum.UN_AUTHORIZED));
    log.info("response : {}", responseStr);
    WebUtil.renderString(response, responseStr);
  }
}
