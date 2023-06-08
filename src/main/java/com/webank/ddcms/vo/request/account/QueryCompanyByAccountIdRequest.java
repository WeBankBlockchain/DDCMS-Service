package com.webank.ddcms.vo.request.account;

import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryCompanyByAccountIdRequest extends CommonRequest {

  @NotNull private Long accountId;
}
