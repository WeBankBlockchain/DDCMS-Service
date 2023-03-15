package com.webank.databrain.authentication;

import com.webank.databrain.authentication.DidAuthentication;
import com.webank.databrain.handler.JwtTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHandler handler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        if(token != null && handler.validateToken(token)) {
            String did = handler.getDidFromToken(token);
            Authentication authentication = new DidAuthentication(did);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
