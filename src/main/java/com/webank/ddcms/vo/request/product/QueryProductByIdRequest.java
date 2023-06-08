package com.webank.ddcms.vo.request.product;

import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductByIdRequest extends CommonRequest {
  private Long productId;
}
