//流控

package imooc.security.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Order(1)     //先流控
public class RateLimitFilter extends OncePerRequestFilter {
    private RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain)
        throws ServletException , IOException{
        if(rateLimiter.tryAcquire()){
            filterChain.doFilter(request,response);
        }else{
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many request");
            response.getWriter().flush();
            return;
        }
    }

}
