package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name ="resources_role" )
public class ResourcesRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESOURCES_ROLE_ID")
    private Long id;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESOURCES_ID")
    private Resources resources;
    
    
    public void setResources(Resources resources) {
        if (this.resources != null) {
            this.resources.getResourcesRoles().remove(this);
        }
        this.resources = resources;
        resources.getResourcesRoles().add(this);
    }
    
    public void setRole(Role role) {
        if (this.role != null) {
            this.role.getResourcesRoles().remove(this);
        }
        this.role = role;
        role.getResourcesRoles().add(this);
    }
    
    
}
