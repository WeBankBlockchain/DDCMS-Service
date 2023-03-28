package com.webank.databrain.controller;

import com.webank.databrain.service.DataSchemaService;
import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.request.dataschema.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("api/schema")
public class DataSchemaController {


    @Autowired
    private DataSchemaService schemaService;




    @PostMapping(value = "/pageQuerySchema")
    public CommonResponse pageQuerySchema(@RequestBody @Valid PageQueryDataSchemaRequest querySchemaRequest) {
        return schemaService.pageQuerySchema(querySchemaRequest);
    }

    @PostMapping(value = "/pageQuerySchemaByHome")
    public CommonResponse pageQuerySchemaByHome(@RequestBody @Valid PageQueryDataSchemaRequest querySchemaRequest) {
        return schemaService.pageQuerySchemaByHome(querySchemaRequest);
    }

    @PostMapping(value = "/pageQueryMySchema")
    public CommonResponse pageQueryMySchema(@RequestBody @Valid PageQueryDataSchemaRequest querySchemaRequest) {
        return schemaService.pageQueryMySchema(querySchemaRequest);
    }

    @PostMapping(value = "/createSchema")
    public CommonResponse createSchema(@RequestBody @Valid CreateDataSchemaRequest createDataSchemaRequest) throws Exception {
        return schemaService.createDataSchema(createDataSchemaRequest);
    }

    @PostMapping(value = "/querySchemaById")
    public CommonResponse querySchemaById(@RequestBody @Valid QuerySchemaByIdRequest request) {
        return schemaService.getDataSchemaById(request.getSchemaId());
    }

    @PostMapping(value = "/querySchemaAccessById")
    public CommonResponse getDataSchemaAccessById(@RequestBody @Valid QuerySchemaAccessByIdRequest request) {
        return schemaService.getDataSchemaAccessById(request.getAccessId());
    }

    @PostMapping(value = "/approveDataSchema")
    public CommonResponse approveDataSchema(@RequestBody @Valid ApproveDataSchemaRequest approveDataSchemaRequest) throws Exception {
        return schemaService.approveDataSchema(approveDataSchemaRequest);
    }

    @PostMapping(value = "/pageQueryMyFavSchema")
    public CommonResponse pageQueryMyFavSchema(@RequestBody @Valid PageQueryMyFavSchemaRequest querySchemaRequest) {
        return schemaService.pageQueryMyFavSchema(querySchemaRequest);
    }

    @PostMapping(value = "/addSchemaFavorite")
    public CommonResponse addSchemaFavorite(@RequestBody @Valid CreateFavSchemaRequest createDataSchemaRequest) throws Exception {
        return schemaService.addSchemaFavorite(createDataSchemaRequest);
    }

    @PostMapping(value = "/delSchemaFavorite")
    public CommonResponse delSchemaFavorite(@RequestBody @Valid DelFavSchemaRequest delFavSchemaRequest) throws Exception {
        return schemaService.delSchemaFavorite(delFavSchemaRequest);
    }

}
