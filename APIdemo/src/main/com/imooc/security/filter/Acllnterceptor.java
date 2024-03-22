//认证

package imooc.security.filter;

import imooc.security.user.User;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.lang.model.type.ReferenceType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Acllnterceptor extends HandlerInterceptorAdapter {

    private String[] permitUrls = new String[]{"/users/login"};   //开辟一个口子做测试
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)
        throws Exception{
        boolean result = true;        //权限认证的结果，过or不过
        if(!ArrayUtils.contains(permitUrls,request.getRequestURI())) {
            User user = (User) request.getAttribute("user");
            if (user == null) {
                response.setContentType("text/plain");
                response.getWriter().write("need authentication");     //需要认证
                response.setStatus(HttpStatus.UNAUTHORIZED.value());     //401
                result = false;
            } else {
                String method = request.getMethod();
                if (!user.hasPermission(method)) {
                    response.setContentType("text/plain");
                    response.getWriter().write("forbidden");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    result = false;
                }
            }
        }

        return result;
    }
}



















