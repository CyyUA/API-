package imooc.security.user;

import org.apache.coyote.http11.upgrade.UpgradeServletInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;



    @GetMapping("/login")
    public void login(@Validated  Userinfo user, HttpServletRequest request) throws IOException{
        Userinfo info = userService.login(user);
        request.getSession().setAttribute("user",info);
    }

    @PutMapping("/{id}")//修改对象
    public Userinfo update(@RequestBody Userinfo user)
    {
        return userService.update(user);
    }

    @DeleteMapping//删除
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("{id}")
    public Userinfo get(@PathVariable Long id, HttpServletRequest request) throws IOException
    {
        User user = (User) request.getAttribute("user");
        if(user == null || !user.getId().equals(id))   //把输入的信息和刚才验证过的放入数组的信息进行比对
                throw new RuntimeException("认证异常");
        return userService.get(id);
    }

    @GetMapping("/test/{name}")
    public List<Userinfo> query(@PathVariable("name") String name)
    {
        return userService.query(name);
    }


}
