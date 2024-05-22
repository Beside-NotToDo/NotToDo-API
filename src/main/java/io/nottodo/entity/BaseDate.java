package io.nottodo.entity;


import jakarta.persistence.Column;
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
    
    
    
    
    @Comment(value = "생성 일자")
    @CreatedDate
    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;
    
    @Comment(value = "수정 일자")
    @LastModifiedDate
    @Column(name = "UPDATE_AT")
    private LocalDateTime updateAt;
    
}
