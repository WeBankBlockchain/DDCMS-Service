package com.webank.databrain.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.webank.databrain.ServerApplicationTests;
import com.webank.databrain.enums.AccountType;
import com.webank.databrain.service.impl.DataSchemaServiceImpl;
import com.webank.databrain.service.impl.ProductServiceImpl;
import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.request.account.ApproveAccountRequest;
import com.webank.databrain.vo.request.account.RegisterRequest;
import com.webank.databrain.vo.request.dataschema.CreateDataSchemaRequest;
import com.webank.databrain.vo.request.dataschema.PageQueryDataSchemaRequest;
import com.webank.databrain.vo.request.dataschema.PageQueryMyFavSchemaRequest;
import com.webank.databrain.vo.request.product.ApproveProductRequest;
import com.webank.databrain.vo.request.product.CreateProductRequest;
import org.assertj.core.util.Lists;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;
import org.fisco.bcos.sdk.v3.transaction.tools.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SchemaServiceTest  extends ServerApplicationTests {

    @Autowired
    private DataSchemaServiceImpl schemaService;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private AccountService accountService;

    @Test
    void schemaQueryTest() throws Exception {
        PageQueryDataSchemaRequest request = new PageQueryDataSchemaRequest();
        request.setPageNo(1);
        request.setPageSize(10);
        CommonResponse result =  schemaService.pageQuerySchema(request);
        System.out.println(JSONUtil.toJsonStr(result));
    }

    @Test
    void createSchemaTest() throws Exception {

        String username = "companyUser" + RandomUtil.randomNumbers(5);

        String password = "12345678";
        RegisterRequest request = new RegisterRequest();
        request.setUserName(username);
        request.setPassword(password);
//        request.setAccountType(AccountType.COMPANY.getRoleKey());
        ProductServiceTest.CompanyDetailRequest companyDetail = new ProductServiceTest.CompanyDetailRequest();
        companyDetail.setCompanyName("头条" + RandomUtil.randomNumbers(5));
        companyDetail.setCompanyDesc("某公司1");
        companyDetail.setCertNo("123456");
        request.setDetailJson(JsonUtils.toJson(companyDetail));
        CommonResponse<String> result = accountService.registerAccount(request);
        System.out.println(JsonUtils.toJson(result));
        String did = result.getData();

        ApproveAccountRequest approveAccountRequest = new ApproveAccountRequest();
        approveAccountRequest.setApproved(true);
        approveAccountRequest.setDid(did);
        accountService.approveAccount(approveAccountRequest);

        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.setProductName("华为P60");
        createProductRequest.setProductDesc("华为P6000手机.." + RandomUtil.randomNumbers(5));
        //createProductRequest.setDid(did);
        CommonResponse response = productServiceImpl.createProduct(createProductRequest);
        System.out.println("response = " + response);

        String productGid = (String) response.getData();
        ApproveProductRequest approveProductRequest = new ApproveProductRequest();
        approveProductRequest.setAgree(true);
        response = productServiceImpl.approveProduct(approveProductRequest);
        System.out.println("response = " + response);


        CreateDataSchemaRequest schemaRequest = new CreateDataSchemaRequest();
        schemaRequest.setContentSchema("{\"test24\":\"String\"....}");
        schemaRequest.setDataSchemaName("华为数据");
        schemaRequest.setAccessCondition("查询条件");
        schemaRequest.setPrice(123);
        schemaRequest.setVersion(1);
        schemaRequest.setDataSchemaUsage("asd");
        schemaRequest.setEffectTime(new Date());
        schemaRequest.setExpireTime(new Date());
        schemaRequest.setDataSchemaDesc("描述");
        schemaRequest.setTagNameList(Lists.newArrayList("科技","金融"));
        schemaRequest.setProductId(1L);
        schemaRequest.setDataFormat(1);
        schemaRequest.setDataProtocol(1);
        schemaRequest.setVisible(1);
        schemaRequest.setUri("127.0.0.1");
        result =  schemaService.createDataSchema(schemaRequest);
        System.out.println(JSONUtil.toJsonStr(result));
    }


    @Test
    void getSchemaDetailTest() throws TransactionException {
        CommonResponse response = schemaService.getDataSchemaById(1L);
        System.out.println("response = " + JSONUtil.toJsonStr(response));
    }


    @Test
    void getSchemaAccessTest() throws TransactionException {
        CommonResponse response = schemaService.getDataSchemaAccessById(1L);
        System.out.println("response = " + JSONUtil.toJsonStr(response));
    }
}
