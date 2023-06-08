package com.webank.ddcms.vo.request.account;

import com.webank.ddcms.vo.common.CommonPageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchAccountRequest extends CommonPageQueryRequest {
  @Pattern(regexp = "0|1|2|3", message = "账户状态不正确")
  private String accountStatus = "0";

  private String keyWord;
}
