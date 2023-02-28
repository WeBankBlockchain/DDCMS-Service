package com.webank.databrain.controller;

import com.webank.databrain.model.common.CommonResponse;
import com.webank.databrain.model.common.Paging;
import com.webank.databrain.model.common.PagingResult;
import com.webank.databrain.model.dataschema.CreateDataSchemaRequest;
import com.webank.databrain.model.dataschema.DataSchemaDetail;
import com.webank.databrain.model.dataschema.DataSchemaDetailWithVisit;
import com.webank.databrain.service.DataSchemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/schema")
public class DataSchemaController {


    @Autowired
    private DataSchemaService schemaService;


    @PostMapping(value = "/pageQuerySchema")
    public CommonResponse<PagingResult<DataSchemaDetail>> pageQuerySchema(
            @RequestParam(name = "pageNo",required = false, defaultValue = "1") int pageNo,
            @RequestParam(name = "pageSize",required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "productId",required = false) String productId,
            @RequestParam(name = "providerId",required = false) String providerId,
            @RequestParam(name = "tagId",required = false, defaultValue = "0") long tagId,
            @RequestParam(name = "keyWord",required = false) String keyWord

    ){
        log.info("pageQuerySchema pageNo = {}, pageSize = {}",pageNo,pageSize);
        PagingResult<DataSchemaDetail> result = schemaService.pageQuerySchema(new Paging(pageNo,pageSize),
                productId,providerId,tagId,keyWord);
        return CommonResponse.createSuccessResult(result);
    }


    @PostMapping(value = "/createSchema")
    public CommonResponse<String> createSchema(@RequestBody CreateDataSchemaRequest createDataSchemaRequest) throws Exception{
        log.info("createSchema did = {}",createDataSchemaRequest.getDid());
        String productId = schemaService.createDataSchema(createDataSchemaRequest);;
        return CommonResponse.createSuccessResult(productId);
    }

    @PostMapping(value = "/querySchemaById")
    public CommonResponse<DataSchemaDetailWithVisit> querySchemaById(@RequestParam(name = "schemaId") String schemaId
    ){
        log.info("querySchemaById schemaId = {}",schemaId);
        DataSchemaDetailWithVisit result = schemaService.getDataSchemaById(schemaId);;
        return CommonResponse.createSuccessResult(result);
    }

}
