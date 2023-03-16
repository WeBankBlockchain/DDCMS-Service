package com.webank.databrain.handler;

import com.webank.databrain.ServerApplicationTests;
import io.jsonwebtoken.Claims;
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

        Claims claims = tokenHandler.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQUZtU1F1SlZQY2hvakxzclRXdzRZc0h3LzVrQUxwNE1laHAyd1Z3cjN3PSIsImlhdCI6MTY3ODk1OTAyMCwiZXhwIjoxNjc4OTU5MTA2fQ.mR2ZwjNZ5Wz-51A4Hv-niTAJknAF62K5uWqlJssCV3Kl0EBgODlQfOv6r411zDXgohokynu8yWtFkyTdoe0d3g");
        System.out.println(claims.getSubject());

//        boolean passedFlag = tokenHandler.parseToken(token);
//        Assertions.assertEquals(true, passedFlag);
    }
}
