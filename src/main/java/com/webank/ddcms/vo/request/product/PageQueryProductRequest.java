package com.webank.ddcms.vo.request.product;

import com.webank.ddcms.vo.common.CommonPageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageQueryProductRequest extends CommonPageQueryRequest {

  private String keyWord;

  private int status = -1;

  private Long providerId;
}
