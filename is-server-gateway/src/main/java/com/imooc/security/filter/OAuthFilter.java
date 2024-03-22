package com.imooc.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
//身份认证
@Slf4j
@Component
public class OAuthFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean shouldFilter() {        //判断过滤器是否起作用
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("oauth start");
        RequestContext requestContext = RequestContext.getCurrentContext();   //获取请求相应对象
        HttpServletRequest request = requestContext.getRequest();//拿到请求

        if(StringUtils.startsWith(request.getRequestURI(),"/token")) {//判断当前请求的uri是不是/token开头
            return null;  //发往认证服务器的请求肯定没有token,还没申请到token
        }

        String authHeader = request.getHeader("Authorization");  //不是token就拿Authorization请求头

        if(StringUtils.isBlank(authHeader)){     //是否为空，认证无论结果如何都需要往下走，不会拦着
            return null;
        }

        if(!StringUtils.startsWithIgnoreCase(authHeader,"bearer ")){  //如果有请求头判断是否以bearer开头
            return null;  //不是就不管，直接跳过
        }

        try{
            TokenInfo info = getTokenInfo(authHeader);
            request.setAttribute("tokenInfo", info);
        } catch (Exception e){
            log.info("get token info fail", e);
        }

          //如果是bearer则要做认证信息校验

        return null;
    }

    private TokenInfo getTokenInfo(String authHeader)
    {
        String token = StringUtils.substringAfter(authHeader,"bearer ");   //把头去掉
        String oauthServiceUrl = "http://localhost:9090/oauth/check_token";
        //开始发请求l
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("gateway", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);    //组成了请求实体

        ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl,HttpMethod.POST,entity, TokenInfo.class);
        //log.info("token info : " + response.getBody().toString());
        return response.getBody();
    }


    @Override
    public String filterType() {
        return "pre";                  //业务逻辑执行之前执行run里的逻辑
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
