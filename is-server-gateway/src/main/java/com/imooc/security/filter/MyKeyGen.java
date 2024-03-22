package com.imooc.security.filter;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.DefaultRateLimitKeyGenerator;
import org.apache.catalina.connector.Request;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

//自定义一个key的生成规则，原有默认的是根据数据库里rate_key字段在yml里设置的type进行时间计算差值达到限流
@Component
public class MyKeyGen extends DefaultRateLimitKeyGenerator {
    public MyKeyGen(RateLimitProperties properties, RateLimitUtils rateLimitUtils){
        super(properties,rateLimitUtils);
    }

    @Override
    public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
        //自己写逻辑生成key,根据key做限流
        return super.key(request, route, policy);    //这方法就是用来重写规则的,police就是yml中的defaultlist
    }
}
