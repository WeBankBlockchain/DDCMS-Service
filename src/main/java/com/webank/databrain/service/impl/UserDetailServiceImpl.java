package com.webank.databrain.service.impl;

import com.webank.databrain.bo.LoginUserBO;
import com.webank.databrain.dao.entity.AccountInfoEntity;
import com.webank.databrain.dao.mapper.AccountInfoMapper;
import com.webank.databrain.enums.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 用户登录验证
        AccountInfoEntity entity = Optional.ofNullable(accountInfoMapper.selectByUserName(username))
                .orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误."));

        // 查询对应的角色
        List<String> permissionList = new ArrayList<>();
        String roleName = AccountType.getAccountType(entity.getAccountType()).getRoleName();
        permissionList.add(roleName);

        // 生成UserDetail对象，放入context holder中
        return new LoginUserBO(entity, permissionList);
    }
}