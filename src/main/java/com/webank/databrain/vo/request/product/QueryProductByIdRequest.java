package com.webank.databrain.vo.request.product;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QueryProductByIdRequest extends CommonRequest {
    @NotBlank(message = "productGid不能为空.")
    private String productGid;

}
