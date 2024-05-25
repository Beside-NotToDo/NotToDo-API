package io.nottodo.repository;

import io.nottodo.entity.NotTodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NotTodoListRepository extends JpaRepository<NotTodoList,Long> {
    
    NotTodoList findByIdAndMemberId(Long id, Long memberId);
    List<NotTodoList> findAllByMemberIdAndCategoryId(Long memberId, Long categoryId);
    List<NotTodoList> findAllByMemberId(Long memberId);
    void deleteAllByMemberId(Long memberId);
    
}
