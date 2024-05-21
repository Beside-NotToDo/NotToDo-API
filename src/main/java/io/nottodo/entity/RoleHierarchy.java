package io.nottodo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;


@Getter
@Entity
@Table(name = "role_hierarchy")

public class RoleHierarchy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "권한 계층 기본키")
    @Column(name = "ROLE_HIERARCHY_ID")
    private Long id;
    
    
    @Column(name = "ROLE_NAME")
    private String roleName;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ROLE_HIERARCHY_ID", insertable = false, updatable = false)
    private RoleHierarchy parent;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<RoleHierarchy> children = new HashSet<>();
    
    
}
