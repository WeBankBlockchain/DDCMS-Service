package com.webank.ddcms.mapper;

import com.webank.ddcms.ServerApplicationTests;
import com.webank.ddcms.dao.mapper.AccountInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CompanyInfoMapperTest extends ServerApplicationTests {

    @Autowired
    private AccountInfoMapper mapper;

    @Test
    public void testTotalCountWithStatus(){
        int totalCount = mapper.totalCountWithStatus(null,0);
        log.info("totalCount: {}", totalCount);
    }
}
