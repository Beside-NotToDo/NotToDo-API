package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Role {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    
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
