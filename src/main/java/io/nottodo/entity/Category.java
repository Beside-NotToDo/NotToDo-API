package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@Getter
public class Category extends BaseDate{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "카테고리 기본키")
    @Column(name = "categoryId")
    private Integer id;
    
    @Comment(value = "카테고리 이름")
    @Column(name = "categoryId")
    private String categoryName;
    
 
}
