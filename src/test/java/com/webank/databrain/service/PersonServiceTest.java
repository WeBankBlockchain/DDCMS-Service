package com.webank.databrain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.databrain.ServerApplicationTests;
import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.request.account.SearchAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PersonServiceTest extends ServerApplicationTests {

    @Autowired
    private PersonService personService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testSearch() throws Exception{
        SearchAccountRequest request = new SearchAccountRequest();
        request.setPageNo(1);
        request.setPageSize(5);
        request.setAccountStatus(1);

        CommonResponse companyResponse = personService.searchPersons(request);
        log.info("response: {}", objectMapper.writeValueAsString(companyResponse));
    }
}
