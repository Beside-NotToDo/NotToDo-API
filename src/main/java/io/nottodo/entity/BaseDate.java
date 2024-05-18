package io.nottodo.entity;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseDate {


    
    @Comment(value = "생성일시")
    @CreatedDate
    private LocalDateTime createAt;
    
    @Comment(value = "수정일시")
    @LastModifiedDate
    private LocalDateTime updateAt;
    
}
