package com.webank.databrain.vo.request.product;

import com.webank.databrain.vo.common.CommonRequest;
import com.webank.databrain.vo.common.HotDataRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest extends CommonRequest {

    @NotBlank(message = "productName不能为空.")
    private String productName;

    @NotBlank(message = "did不能为空.")
    private String did;

    @NotBlank(message = "productDesc不能为空.")
    private String productDesc;
}
