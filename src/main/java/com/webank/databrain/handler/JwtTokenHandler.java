package com.webank.databrain.handler;

import com.webank.databrain.config.JWTConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenHandler {

  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";

  @Autowired private JWTConfig jwtConfig;

  private Key signKey;

  @PostConstruct
  public void initKey() {
    signKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
  }

  public String generateToken(String did) {
    return Jwts.builder()
        .setSubject(did)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
        .signWith(signKey)
        .compact();
  }

  public String getDidFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(signKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public Date getExpirationFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(signKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();
  }

  public Claims parseToken(String token) {
    return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
  }

  public boolean isTokenExpired(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(signKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .before(new Date());
  }
}
