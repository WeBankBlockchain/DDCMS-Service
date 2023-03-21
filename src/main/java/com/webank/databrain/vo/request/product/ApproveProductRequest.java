package com.webank.databrain.vo.request.product;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApproveProductRequest extends CommonRequest {

    @NotBlank(message = "productGId不能为空.")
    private String productGId;

    @NotBlank(message = "did不能为空.")
    private String did;

    private Long productId;

    private boolean agree;

}
