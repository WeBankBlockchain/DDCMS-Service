package com.webank.ddcms.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.webank.ddcms.ServerApplicationTests;
import com.webank.ddcms.enums.AccountType;
import com.webank.ddcms.service.impl.ProductServiceImpl;
import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.common.HotDataRequest;
import com.webank.ddcms.vo.request.account.ApproveAccountRequest;
import com.webank.ddcms.vo.request.account.RegisterRequest;
import com.webank.ddcms.vo.request.product.ApproveProductRequest;
import com.webank.ddcms.vo.request.product.CreateProductRequest;
import com.webank.ddcms.vo.request.product.PageQueryProductRequest;
import lombok.Data;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;
import org.fisco.bcos.sdk.v3.transaction.tools.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceTest extends ServerApplicationTests {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private AccountService accountService;


    @Test
    void productQueryTest() throws Exception {
        PageQueryProductRequest request = new PageQueryProductRequest();
        request.setStatus(0);
        CommonResponse result =  productServiceImpl.pageQueryProducts(request);
        System.out.println(JSONUtil.toJsonStr(result));
    }


    @Test
    void productHotTest() throws Exception {
        CommonResponse result =  productServiceImpl.getHotProducts(new HotDataRequest());
        System.out.println(JSONUtil.toJsonStr(result));
    }

    @Data
    static
    class CompanyDetailRequest{
        private String companyName;

        private String companyDesc;

        private String CertNo;

        private String companyCertType = "1";


        private String companyCertNo = "123";
        private String companyCertFileUri = "128.2.1.2";
        private String companyContact = "123123";

    }

    @Test
    void createProductTest() throws Exception {
        String username = "companyUser"+ RandomUtil.randomNumbers(5);
        String password = "12345678";
        RegisterRequest request = new RegisterRequest();
        request.setUserName(username);
        request.setPassword(password);
        request.setAccountType(AccountType.COMPANY.getRoleKey()+"");
        CompanyDetailRequest companyDetail = new CompanyDetailRequest();
        companyDetail.setCompanyName("腾讯" + RandomUtil.randomNumbers(5));
        companyDetail.setCompanyDesc("某公司2");
        companyDetail.setCertNo("123456");
        companyDetail.setCompanyCertType("1");
        request.setDetailJson(JsonUtils.toJson(companyDetail));
        CommonResponse<String> result = accountService.registerAccount(request);
        System.out.println(JsonUtils.toJson(result));
        String did = result.getData();

        ApproveAccountRequest approveAccountRequest = new ApproveAccountRequest();
        approveAccountRequest.setApproved(true);
        approveAccountRequest.setDid(did);
        accountService.approveAccount(approveAccountRequest);

        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.setProductName("华为P6000");
        createProductRequest.setProductDesc("华为P600手机..." + RandomUtil.randomNumbers(5));
        //createProductRequest.setDid(did);
        CommonResponse response = productServiceImpl.createProduct(createProductRequest);
        System.out.println("response = " + response);

        ApproveProductRequest approveProductRequest = new ApproveProductRequest();
        approveProductRequest.setAgree(true);
        response = productServiceImpl.approveProduct(approveProductRequest);
        System.out.println("response = " + response);
    }


    @Test
    void getProductDetailTest() throws TransactionException {
        CommonResponse response = productServiceImpl.getProductDetail(1L);
        System.out.println("response = " + response);
    }


}
