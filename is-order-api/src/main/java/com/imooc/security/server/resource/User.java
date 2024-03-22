package com.imooc.security.server.resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class User implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Long id;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
    }

    @Override
    public boolean isAccountNonExpired() {    //用户是不是没过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {     //是不是没被锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  //密码是不是没过期
        return true;
    }

    @Override
    public boolean isEnabled() {       //账户是不是可用的
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
