package com.webank.databrain.utils;

import io.jsonwebtoken.Jwts;

import java.sql.Timestamp;

public class JWTUtil {

    public static String createJWT(String subject, String issue, Object claim, long intervalTime) {

//        long expireTime = System.currentTimeMillis() + intervalTime;
//        String result = Jwts.builder()
//                .setSubject(subject) //设置主题
//                .setIssuer(issue) //发行者
//                .setId(issue)//jwtID
//                .setExpiration(new Timestamp(expireTime)) //设置过期日期
//                .claim("user", claim)//主题，可以包含用户信息
//                .signWith(getSignatureAlgorithm(), getSignedKey())//加密算法
//                .compressWith(CompressionCodecs.DEFLATE).compact();//对载荷进行压缩
//
//        return result;
        return null;
    }
}
