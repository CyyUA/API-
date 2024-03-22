package imooc.security.user;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
public class Userinfo {
        private Long id;
        private String name;
        @NotNull(message = "error : username is null")
        private String username;
        @NotNull(message = "error : password is null")
        private String password;

}
