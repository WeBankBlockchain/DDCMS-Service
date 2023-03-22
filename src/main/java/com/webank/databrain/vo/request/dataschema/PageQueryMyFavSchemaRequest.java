package com.webank.databrain.vo.request.dataschema;

import com.webank.databrain.vo.common.CommonPageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageQueryMyFavSchemaRequest extends CommonPageQueryRequest {

    private String keyWord;

    private int state = -1;

}
