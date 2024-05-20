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
@Table(name = "ROLE")
public class Role {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "권한 기본키")
    @Column(name = "ROLE_ID")
    private Long id;
    
    @Comment(value = "권한 이름")
    @Column(name = "ROLE_NAME")
    private String roleName;
    
    
    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    List<MemberRole> memberRoles = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    List<ResourcesRole> resourcesRoles = new ArrayList<>();
    

    
    public static Role createRole(String name) {
        Role role = new Role();
        role.roleName = name;
        return role;
    }
    
}
