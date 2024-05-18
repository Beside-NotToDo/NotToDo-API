package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resources_id")
    private Long id;
    
    @Column(name = "resource_name")
    private String resourceName;
    
    @Column(name = "http_method")
    private String httpMethod;
    
    @Column(name = "order_num")
    private int orderNum;
    
    @Column(name = "resource_type")
    private String resourceType;
    
    
    @OneToMany(mappedBy = "resources")
    @ToString.Exclude
    List<ResourcesRole> resourcesRoles = new ArrayList<>();
    
    
}
