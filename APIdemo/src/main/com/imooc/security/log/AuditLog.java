package imooc.security.log;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.lang.ref.SoftReference;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String method;
    private String path;
    private Integer status;           //HTTP返回的状态码

    private String username;          //哪个用户的请求
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedBy
    private Date modifyTime;


}
