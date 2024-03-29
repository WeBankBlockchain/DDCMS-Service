package com.webank.ddcms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.ddcms.ServerApplicationTests;
import com.webank.ddcms.vo.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MenuServiceTest extends ServerApplicationTests {

    @Autowired
    private MenuService menuService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetMenus() throws JsonProcessingException {
        CommonResponse response  = menuService.getMenuByRole();
        log.info("response : {}", objectMapper.writeValueAsString(response));
    }
}
