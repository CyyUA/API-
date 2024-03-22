package com.imooc.security.filter;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class MyRateLimitErrorHandler extends DefaultRateLimiterErrorHandler {
    //限流发生错误会在这儿来处理
    @Override
    public void handleError(String msg, Exception e) {
        super.handleError(msg, e);
    }
}
