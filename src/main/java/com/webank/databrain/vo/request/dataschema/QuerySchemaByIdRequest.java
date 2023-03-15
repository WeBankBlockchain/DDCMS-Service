package com.webank.databrain.vo.request.dataschema;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QuerySchemaByIdRequest extends CommonRequest {

    @NotBlank(message = "schemaGid不能为空.")
    private String schemaGid;

}
