package com.webank.databrain.vo.request.account;

import com.webank.databrain.vo.common.CommonRequest;
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
