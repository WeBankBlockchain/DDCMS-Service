package com.webank.databrain.service;

import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.request.dataschema.CreateDataSchemaRequest;
import com.webank.databrain.vo.request.dataschema.PageQueryDataSchemaRequest;
import com.webank.databrain.vo.request.dataschema.UpdateDataSchemaRequest;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;


public interface DataSchemaService {

    CommonResponse pageQuerySchema(PageQueryDataSchemaRequest request);

    CommonResponse pageQueryMySchema(PageQueryDataSchemaRequest request);

    CommonResponse getDataSchemaById(Long schemaId);

    CommonResponse getDataSchemaAccessById(Long accessId);

    CommonResponse updateDataSchema(UpdateDataSchemaRequest schemaRequest) throws TransactionException;

    CommonResponse createDataSchema(CreateDataSchemaRequest schemaRequest) throws Exception;
}
