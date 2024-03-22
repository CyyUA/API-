package imooc.security.user;

import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @NotBlank(message = "error : username is null")
    @Column(unique = true)                   //用户唯一性检验
    private String username;
    @NotNull(message = "error : password is null")
    private String password;

    private String permissions;       //存储用户权限
    public Userinfo buildInfo(){
        Userinfo info = new Userinfo();
        BeanUtils.copyProperties(this,info);
        return info;
    }

    public boolean hasPermission(String method)
    {
        boolean result = false;
        if(StringUtils.equalsAnyIgnoreCase("get",method)){
            result = StringUtils.contains(permissions,"r");           //看数据库中的权限
    }else{
            result = StringUtils.contains(permissions,"w");
        }
        return result;
    }
}
