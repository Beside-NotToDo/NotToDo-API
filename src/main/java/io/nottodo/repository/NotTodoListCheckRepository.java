package io.nottodo.repository;

import io.nottodo.entity.NotTodoListCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NotTodoListCheckRepository extends JpaRepository<NotTodoListCheck, Long> {
    Optional<NotTodoListCheck> findByNotTodoListIdAndCheckDate(Long notTodoListId, LocalDate checkDate);
    
    List<NotTodoListCheck> findAllByNotTodoListIdInAndIsCompliantTrue(List<Long> notTodoListIds);
    List<NotTodoListCheck> findAllByNotTodoListId(Long notTodoListId);
    List<NotTodoListCheck> findAllByNotTodoListIdIn(List<Long> notTodoListIds);
    
    long countByNotTodoListIdAndIsCompliantTrue(Long notTodoListId);
}
