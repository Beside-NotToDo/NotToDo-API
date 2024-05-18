package io.nottodo.repository;


import io.nottodo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    
    Member findByUsername(String username);
}
