package com.webank.databrain.handler;

import com.webank.databrain.ServerApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtTest extends ServerApplicationTests {


    @Autowired
    private JwtTokenHandler tokenHandler;
    @Test
    public void testGenerateAndVerify(){
        String did = "aaa";
        String token = tokenHandler.generateToken(did);
        System.out.println(token);

        boolean passedFlag = tokenHandler.validateToken(token);
        Assertions.assertEquals(true, passedFlag);
    }
}
