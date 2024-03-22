//记录日志


package imooc.security.filter;
import imooc.security.log.AuditLog;
import imooc.security.log.AuditLogRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuditLogInterceptor extends HandlerInterceptorAdapter {    //Hand这个类有4方法，日志前和日志后
    @Autowired
    private AuditLogRepositry auditLogRepositry;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception{
        AuditLog log = new AuditLog();
        log.setMethod(request.getMethod());
        log.setPath(request.getRequestURI());

        auditLogRepositry.save(log);
        request.setAttribute("auditLogId",log.getId());
        return true;
    }


    //请求结束之后获取状态码，看这个请求究竟是什么状态
   public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex)
       throws Exception{
         Long auditLogId =(Long) request.getAttribute("auditLogId");
         AuditLog log = auditLogRepositry.findById(auditLogId).get();
         log.setStatus(response.getStatus());
         auditLogRepositry.save(log);
     }
}
