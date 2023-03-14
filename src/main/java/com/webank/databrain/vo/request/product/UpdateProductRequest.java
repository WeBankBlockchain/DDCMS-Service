package com.webank.databrain.vo.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    @NotBlank(message = "productId不能为空.")
    private Long productId;

    @NotBlank(message = "productGId不能为空.")
    private String productGId;

    @NotBlank(message = "productName不能为空.")
    private String productName;

    @NotBlank(message = "productDesc不能为空.")
    private String productDesc;
}
