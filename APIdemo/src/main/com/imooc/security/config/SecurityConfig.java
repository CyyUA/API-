package imooc.security.config;

import imooc.security.filter.Acllnterceptor;
import imooc.security.filter.AuditLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
public class SecurityConfig  implements WebMvcConfigurer {
    @Autowired
    private AuditLogInterceptor auditLogInterceptor;
    @Autowired
    private Acllnterceptor acllnterceptor;
//生效的顺序按照加入的顺序
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInterceptor);
        registry.addInterceptor(acllnterceptor);
    }



}
