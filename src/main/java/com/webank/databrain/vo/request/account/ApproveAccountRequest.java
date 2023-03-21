package com.webank.databrain.vo.request.account;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApproveAccountRequest extends CommonRequest {
    private boolean approved = false;
}

