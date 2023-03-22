package com.webank.databrain.vo.request.dataschema;

import com.webank.databrain.vo.common.CommonPageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageQueryDataSchemaRequest extends CommonPageQueryRequest {
    private Long productId;
    private Long providerId;
    private String keyWord;
    private int state = -1;
}
