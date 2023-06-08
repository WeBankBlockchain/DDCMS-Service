package com.webank.ddcms.vo.request.account;

import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryByUsernameRequest extends CommonRequest {
  @NotBlank(message = "用户名不能为空.")
  private String userName;
}
