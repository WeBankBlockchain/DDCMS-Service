package com.webank.databrain.bo;


import com.webank.databrain.dao.entity.AccountInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LoginInfoBo implements UserDetails {


    private AccountInfoEntity entity;

    private List<String> permissions;

    private List<SimpleGrantedAuthority> grantedAuthorities;

    public LoginInfoBo(AccountInfoEntity entity, List<String> permissions){
        this.entity = entity;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(null != grantedAuthorities){
            return grantedAuthorities;
        }
        grantedAuthorities = permissions.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return entity.getPwdHash();
    }

    @Override
    public String getUsername() {
        return entity.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}