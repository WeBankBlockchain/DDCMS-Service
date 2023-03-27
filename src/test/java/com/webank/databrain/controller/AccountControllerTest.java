package com.webank.databrain.controller;

import com.webank.databrain.ServerApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
public class AccountControllerTest extends ServerApplicationTests {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAccountController() throws Exception {
        RequestBuilder requestBuilder = null;

        // 注册一个见证方
        requestBuilder = MockMvcRequestBuilders.post("/api/account/register").contentType("application/json")
                .content("{\"userName\":\"jzf1\",\"password\":\"1\",\"accountType\":\"2\",\"detailJson\":\"{\\\"companyName\\\":\\\"见证方1\\\",\\\"companyContact\\\":\\\"122\\\",\\\"companyCertType\\\":\\\"busiID\\\",\\\"companyCertNo\\\":\\\"jzf1\\\",\\\"companyCertFileUri\\\":\\\"958cb3c58c3c4df78edcf4b6bdbeaf97.png\\\"}\"}")
        ;
        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.content().string("{\"code\":0,\"msg\":\"success\",\"debugMsg\":null,\"data\":null}"));
    }

}
