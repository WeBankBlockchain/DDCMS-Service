package com.webank.ddcms.vo.request.account;

import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApproveAccountRequest extends CommonRequest {
  @NotBlank(message = "did不能为空.")
  private String did;

  private boolean approved = false;
}
