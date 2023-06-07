package com.webank.databrain.filter;

import com.webank.databrain.bo.LoginUserBO;
import com.webank.databrain.dao.entity.AccountInfoEntity;
import com.webank.databrain.dao.mapper.AccountInfoMapper;
import com.webank.databrain.enums.AccountType;
import com.webank.databrain.handler.JwtTokenHandler;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHandler handler;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get token
        String token = request.getHeader(JwtTokenHandler.TOKEN_HEADER);
        if(StringUtils.isEmpty(token)||!token.startsWith("Bearer ")){
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        AccountInfoEntity entity = this.getAccount(token);

        List<String> permissionList = new ArrayList<>();

        // 需要从数据库的权限表中查询，然后封装
        permissionList.add(AccountType.getAccountType(entity.getAccountType()).getRoleName());

        LoginUserBO bo = new LoginUserBO(entity, permissionList);
        //save to ContextHolder
        //获取权限信息封装到Authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(bo, null, bo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    public AccountInfoEntity getAccount(String token) {

        String did;
        try {
            Claims claims = handler.parseToken(token);
            did = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("token解析失败.");
        }

        AccountInfoEntity account = accountInfoMapper.selectByDid(did);

        if (account == null) {
            throw new RuntimeException("用户不存在.");
        }

        return account;
    }
}
