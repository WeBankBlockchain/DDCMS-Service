package com.webank.databrain.vo.request.account;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryCompanyByAccountIdRequest extends CommonRequest {

    @NotNull
    private Long accountId;

}
