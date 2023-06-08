package com.webank.ddcms.vo.request.dataschema;

import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateFavSchemaRequest extends CommonRequest {

  private Long schemaId;
}
