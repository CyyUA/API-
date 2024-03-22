package com.imooc.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.javafx.tk.TKClipboard;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

//授权过滤器
@Component
@Slf4j
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("authorization start");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();                    //拿到请求
        if(isNeedAuth(request)){      //判断是否需要认证
            TokenInfo tokenInfo = (TokenInfo)request.getAttribute("tokenInfo");  //拿出之前的tokenInfo
            if(tokenInfo != null && tokenInfo.isActive()){               //本来应该是token!=null && tokenInfo.isActive()
                if(!hasPermission(tokenInfo,request)){    //判断是否有权限，根据令牌里的信息（用户名等），以及request
                    log.info("audit log update fail 403");
                    handleError(403,requestContext);
                }
            } else {   //要报错了
                if(!StringUtils.startsWith(request.getRequestURI(),"/token")){   //之前说如果是token的请求不会报错，所以是token不会报错，否则会401
                    log.info("audit log update fail 401");
                    handleError(401,requestContext);    //处理错误
                }
            }
        }

        return null;
    }

    private boolean hasPermission(TokenInfo tokenInfo,HttpServletRequest request){
        return true;//RandomUtils.nextInt() % 2 == 0;      //不写具体实现了，随机数取模表示一半几率通过
    }

    private void handleError(int status, RequestContext requestContext)   //401，403
    {
        requestContext.getResponse().setContentType("application/json");
        requestContext.setResponseStatusCode(status);   //相应的状态码就是当前status传进来的401或者403
        requestContext.setResponseBody("{\"message\":\"auth fail\"}") ;   //真正返回的信息json格式
        requestContext.setSendZuulResponse(false);   //就不会往后走了，如果接着往后走会调到run中的return,最后又走到业务服务。到这儿就彻底停止了
    }


    private boolean isNeedAuth(HttpServletRequest request){    //可以填充判断权限的操作，查数据库啊
        return true;   //永远返回true
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }
}
