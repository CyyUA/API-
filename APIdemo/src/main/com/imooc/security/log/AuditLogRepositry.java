package imooc.security.log;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface AuditLogRepositry extends JpaSpecificationExecutor<AuditLog>, CrudRepository<AuditLog,Long> {

}
