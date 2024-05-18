package io.nottodo.repository;


import io.nottodo.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchRepository extends JpaRepository<RoleHierarchy,Long> {
}
