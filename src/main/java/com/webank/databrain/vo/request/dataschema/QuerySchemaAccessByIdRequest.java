package com.webank.databrain.vo.request.dataschema;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuerySchemaAccessByIdRequest extends CommonRequest {

    @NotBlank(message = "accessId不能为空.")
    private Long accessId;
}
