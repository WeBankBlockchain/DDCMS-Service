package com.webank.databrain.service.impl;

import com.webank.databrain.bo.LoginInfoBo;
import com.webank.databrain.dao.entity.AccountInfoEntity;
import com.webank.databrain.dao.mapper.AccountInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountInfoEntity entity = accountInfoMapper.selectByUserName(username);
        if(null == entity){
            throw new RuntimeException("用户名或密码错误.");
        }

        //TODO: 查询对应的权限

        return new LoginInfoBo(entity);
    }
}