package com.webank.databrain.vo.request.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QueryProductByIdRequest {
    @NotBlank(message = "productGid不能为空.")
    private String productGid;

}
