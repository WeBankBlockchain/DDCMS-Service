package com.webank.databrain.mapper;

import com.webank.databrain.ServerApplicationTests;
import com.webank.databrain.dao.mapper.AccountInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class PersonInfoMapperTest extends ServerApplicationTests {

    @Autowired
    private AccountInfoMapper mapper;

    @Test
    public void testTotalCountWithStatus(){
        int totalCount = mapper.totalCountWithStatus(0, "ha",-1);
        log.info("totalCount: {}", totalCount);
    }
}
