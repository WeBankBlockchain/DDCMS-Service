package com.webank.ddcms.vo.request.dataschema;

import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApproveDataSchemaRequest extends CommonRequest {

  private Long schemaId;

  private boolean agree;
}
