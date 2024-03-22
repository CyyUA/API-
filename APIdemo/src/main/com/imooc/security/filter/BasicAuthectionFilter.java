//认证


package imooc.security.filter;
import com.lambdaworks.crypto.SCryptUtil;
import imooc.security.user.User;
import imooc.security.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
//base64认证，加密后的在请求头中

@Component
@Order(2)
public class BasicAuthectionFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain)
            throws ServletException, IOException{
        String authHeader = request.getHeader("Authorization");
        if(StringUtils.isNotBlank(authHeader))                                                          //有的请求不需要九米请求头，所有判断请求头是否为空，不为空再做下一步
        {
            String token64 =  StringUtils.substringAfter(authHeader,"Basic ");                            //拿到经过base64编码的Token,在basic后面
            String token = new String(Base64Utils.decodeFromString(token64));        //从串里解析处密文
            String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(token,":");                                       //根据：将解析出的铭文拆成两个字符串.stringutils做字符串操作
            String username = items[0];
            String password = items[1];

            //数据库查询
            User user = userRepository.findByUsername(username);
            if(user != null && SCryptUtil.check(password,user.getPassword())){       //验证明文和加密串，SCryputil就是验证加密后的密码
                request.setAttribute("user",user);
            }
        }
        filterChain.doFilter(request,response);    //到下一个链


    }

}
