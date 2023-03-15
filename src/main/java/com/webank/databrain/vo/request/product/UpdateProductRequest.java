package com.webank.databrain.vo.request.product;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest extends CommonRequest {

    @NotBlank(message = "productId不能为空.")
    private Long productId;

    @NotBlank(message = "productGId不能为空.")
    private String productGId;

    @NotBlank(message = "productName不能为空.")
    private String productName;

    @NotBlank(message = "productDesc不能为空.")
    private String productDesc;
}
