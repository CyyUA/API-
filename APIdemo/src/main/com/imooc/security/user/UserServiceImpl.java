package imooc.security.user;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    public Userinfo login(Userinfo info) throws
            IOException{
        User user = new User();
        BeanUtils.copyProperties(info,user);
        user.setPassword(SCryptUtil.scrypt(user.getPassword(),32768,8,1));     //用户密码加密
        userRepository.save(user);
        info.setId(user.getId());
        return info;
    }

    public Userinfo update(Userinfo user){
          return null;
    }

    public void delete(Long id){

    }

    public Userinfo get(Long id) throws IOException {
        return userRepository.findById(id).get().buildInfo();  //转成一个userinfo对象
    }

    public List<Userinfo> query(String name){
        return null;
    }

}
