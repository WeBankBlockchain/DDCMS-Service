package com.webank.databrain.vo.request.dataschema;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QuerySchemaByIdRequest {

    @NotBlank(message = "schemaGid不能为空.")
    private String schemaGid;

}
