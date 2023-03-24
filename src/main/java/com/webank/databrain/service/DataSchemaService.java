package com.webank.databrain.service;

import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.request.dataschema.*;


public interface DataSchemaService {

    CommonResponse pageQuerySchema(PageQueryDataSchemaRequest request);

    CommonResponse pageQueryMySchema(PageQueryDataSchemaRequest request);

    CommonResponse pageQueryMyFavSchema(PageQueryMyFavSchemaRequest request);

    CommonResponse addSchemaFavorite(CreateFavSchemaRequest request);

    CommonResponse delSchemaFavorite(DelFavSchemaRequest request);


    CommonResponse getDataSchemaById(Long schemaId);

    CommonResponse getDataSchemaAccessById(Long accessId);

    CommonResponse approveDataSchema(ApproveDataSchemaRequest request);

    CommonResponse createDataSchema(CreateDataSchemaRequest schemaRequest) throws Exception;
}
