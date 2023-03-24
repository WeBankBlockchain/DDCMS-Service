package com.webank.databrain.service;

import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.request.dataschema.*;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;


public interface DataSchemaService {

    CommonResponse pageQuerySchema(PageQueryDataSchemaRequest request);

    CommonResponse pageQueryMySchema(PageQueryDataSchemaRequest request);

    CommonResponse pageQueryMyFavSchema(PageQueryMyFavSchemaRequest request);

    CommonResponse addSchemaFavorite(CreateFavSchemaRequest request);

    CommonResponse delSchemaFavorite(DelFavSchemaRequest request);


    CommonResponse getDataSchemaById(Long schemaId);

    CommonResponse getDataSchemaAccessById(Long accessId);

    CommonResponse approveDataSchema(ApproveDataSchemaRequest request) throws TransactionException;

    CommonResponse createDataSchema(CreateDataSchemaRequest schemaRequest) throws Exception;
}
