package imooc.security.user;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@Service
public interface UserService {
    Userinfo login(Userinfo user) throws IOException ;
    Userinfo update(Userinfo user);
    void delete(Long id);
    Userinfo get(Long id) throws IOException;
    List<Userinfo> query(String name);
}
