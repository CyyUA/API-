package com.imooc.security.filter;

import lombok.Data;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Date;
@Data
public class TokenInfo {
    private boolean active;   //令牌是否可用
    private String client_id;  //是发给哪个客户端应用
    private String[] scope;  //令牌的scope组
    private String user_name;  //要发给谁
    private String[] aud;   //可以访问哪些资源服务器的id
    private Date exp;  //过期时间
    private String[] authorities;     //发令牌的时候给的用户权限，ROLE_ADMIN

}
