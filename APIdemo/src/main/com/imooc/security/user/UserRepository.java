package imooc.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaSpecificationExecutor<User> , CrudRepository<User,Long> {
    List<Userinfo> findByName(String name);
    User findByUsername(String username);
}
