package io.nottodo.repository;


import io.nottodo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role,Long> {
    
    @Query("SELECT r FROM Role r WHERE r.roleName = :roleName")
    Role findByName(String roleName);
}
