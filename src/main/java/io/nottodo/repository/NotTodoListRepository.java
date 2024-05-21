package io.nottodo.repository;

import io.nottodo.entity.NotTodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotTodoListRepository extends JpaRepository<NotTodoList,Long> {
    
    NotTodoList findByIdAndMemberId(Long id, Long memberId);
}
