package io.nottodo.entity;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseUser extends BaseDate{

    
    @Comment(value = "등록자")
    @CreatedBy
    private String createName;
    
    @Comment(value = "수정자")
    @LastModifiedBy
    private String updateName;
}
