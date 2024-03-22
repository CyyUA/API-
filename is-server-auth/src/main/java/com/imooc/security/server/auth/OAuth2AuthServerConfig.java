package com.imooc.security.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer   //提供token
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;   //用来验证和用户相关的信息
    //发令牌的条件
    @Autowired
    private PasswordEncoder passwordEncoder;          //密码加密,随机盐做加密
    @Autowired
    private DataSource dataSource;

    //存储token
    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }
    //从数据库读取客户端信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{        //配置客户端信息，哪些客户端会访问
        clients.jdbc(dataSource);
    }

  //从数据源的表里取token
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager);

    }
    //谁能找我验令牌，需要什么样的条件
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security.checkTokenAccess("isAuthenticated()");    //要进行token验证一定要过身份验证的，上个方法
    }
}
