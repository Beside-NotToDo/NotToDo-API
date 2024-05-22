package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
public class Category {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "카테고리 기본키")
    @Column(name = "CATEGORY_ID")
    private Long id;
    
    @Comment(value = "카테고리 이름")
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    
    @OneToMany(mappedBy = "category")
    private List<NotTodoList> notTodoLists = new ArrayList<>();
 
}
