package com.webank.ddcms.vo.request.account;

import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterRequest extends CommonRequest {
  @NotBlank(message = "用户名不能为空.")
  private String userName;

  @NotBlank(message = "密码不能为空.")
  private String password;

  @Pattern(regexp = "[12]", message = "accountType must be 1 or 2")
  private String accountType;

  @NotBlank(message = "注册详细信息不能为空.")
  private String detailJson;

  private String hexPrivateKey;
}
