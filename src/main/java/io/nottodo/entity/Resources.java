package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "리소스 기본키")
    @Column(name = "RESOURCES_ID")
    private Long id;
    
    @Comment(value = "리소스 이름")
    @Column(name = "RESOURCE_NAME")
    private String resourceName;
    
    @Comment(value = "http메소드")
    @Column(name = "HTTP_METHOD")
    private String httpMethod;
    
    @Comment(value = "정렬 순서")
    @Column(name = "ORDER_NUM")
    private int orderNum;
    
    
    @OneToMany(mappedBy = "resources")
    @ToString.Exclude
    List<ResourcesRole> resourcesRoles = new ArrayList<>();
    
    
}
