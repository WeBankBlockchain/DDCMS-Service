package com.webank.ddcms.service;

import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.request.dataschema.*;
import com.webank.ddcms.vo.request.dataschema.*;
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
