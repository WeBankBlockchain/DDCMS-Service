package com.webank.ddcms.vo.request.dataschema;

import com.webank.ddcms.vo.common.CommonPageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageQueryDataSchemaRequest extends CommonPageQueryRequest {
  private Long productId;
  private Long providerId;
  private String keyWord;
  private Long tagId;
  private int status = -1;
}
