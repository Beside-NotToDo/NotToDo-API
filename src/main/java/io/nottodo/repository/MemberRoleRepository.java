package io.nottodo.repository;


import io.nottodo.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole,Long> {
}
