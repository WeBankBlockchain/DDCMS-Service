package com.webank.ddcms.vo.request.product;

import com.webank.ddcms.vo.common.CommonRequest;
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

  @NotBlank(message = "productDesc不能为空.")
  private String productDesc;
}
