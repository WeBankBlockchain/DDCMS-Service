package com.webank.databrain.service;

import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.request.dataschema.CreateDataSchemaRequest;
import com.webank.databrain.vo.request.dataschema.PageQueryDataSchemaRequest;

public interface DataSchemaService {

    CommonResponse pageQuerySchema(PageQueryDataSchemaRequest request);

    CommonResponse getDataSchemaByGid(String schemaGid);

    CommonResponse getDataSchemaAccessById(Long accessId);

    CommonResponse createDataSchema(CreateDataSchemaRequest schemaRequest) throws Exception;
}
