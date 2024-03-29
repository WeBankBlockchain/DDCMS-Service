package com.webank.ddcms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.ddcms.ServerApplicationTests;
import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.common.HotDataRequest;
import com.webank.ddcms.vo.request.account.SearchAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CompanyServiceTest extends ServerApplicationTests {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testHotCompanies() throws Exception{
        CommonResponse response = companyService.listHotCompanies(new HotDataRequest());
        log.info("response: {}", objectMapper.writeValueAsString(response));
    }

    @Test
    public void testListCompanyByPage() throws Exception{
//        PageQueryCompanyRequest request = new PageQueryCompanyRequest();
//        request.setPageNo(1);
//        request.setPageSize(2);
//        Object response = companyService.listCompanyByPage(request);
//        System.out.println(JsonUtils.toJson(response));
    }

    @Test
    public void testQueryByUsername() throws Exception{
//        QueryByUsernameRequest request = new QueryByUsernameRequest();
//        request.setUsername("personalUser0002");
//
//        Object personResponse = companyService.getPersonByUsername(request.getUsername());
//        System.out.println(JsonUtils.toJson(personResponse));
//
//        request.setUsername("companyUser00001");
//        Object companyResponse = companyService.getCompanyByUsername(request.getUsername());
//        System.out.println(JsonUtils.toJson(companyResponse));

    }

    @Test
    public void testSearch() throws Exception{
        SearchAccountRequest companyRequest = new SearchAccountRequest();
        companyRequest.setPageNo(1);
        companyRequest.setPageSize(10);

        CommonResponse companyResponse = companyService.searchCompanies(companyRequest);
        log.info("response: {}", objectMapper.writeValueAsString(companyResponse));
    }
}
