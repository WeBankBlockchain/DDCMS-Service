package com.webank.databrain.vo.request.dataschema;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuerySchemaByIdRequest extends CommonRequest {

    @NotBlank(message = "目录DID不能为空.")
    private String schemaGid;
}
