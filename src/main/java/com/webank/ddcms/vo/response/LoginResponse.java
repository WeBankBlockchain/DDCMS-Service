package com.webank.ddcms.vo.response;

import lombok.Data;

@Data
public class LoginResponse {
  private String token;
  private String userName;
  private String accountType;
}
