package com.webank.ddcms.vo.request.product;

import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApproveProductRequest extends CommonRequest {

  private Long productId;

  private boolean agree;
}
