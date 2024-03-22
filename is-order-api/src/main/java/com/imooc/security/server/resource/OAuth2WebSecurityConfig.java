package com.imooc.security.server.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.*;

@Configuration
@EnableWebSecurity      //控制授权认证的一些事
public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public ResourceServerTokenServices tokenServices(){          //资源服务器的令牌服务
        RemoteTokenServices tokenServices = new RemoteTokenServices();    //调用远程服务
        tokenServices.setClientId("orderService");     //告诉我是谁
        tokenServices.setClientSecret("123456");
        tokenServices.setCheckTokenEndpointUrl("http://localhost:9090/oauth/check_token");        //调什么样的服务七校验token,在这个地址验token
        tokenServices.setAccessTokenConverter(getAccessTokenConverter());//把令牌转换成用户信息
        return tokenServices;
    }

    //做的所有都是为了在controller中请求的时候能拿到用户信息而不是只string一个用户名
    private AccessTokenConverter getAccessTokenConverter(){
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
        userTokenConverter.setUserDetailsService(userDetailsService);   //把用户名换成User用户对象
        accessTokenConverter.setUserTokenConverter(userTokenConverter);  //把token里的用户名信息转成你想要的用户信息
        return accessTokenConverter;
    }

    //所谓的验token就是拿token获取用户信息,问一下认证服务器令牌对应的用户信息是什么

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setTokenServices(tokenServices());
        return authenticationManager;
    }
}
