package io.nottodo.repository;

import io.nottodo.entity.NotTodoListCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NotTodoListCheckRepository extends JpaRepository<NotTodoListCheck, Long> {

    List<NotTodoListCheck> findAllByNotTodoListIdIn(List<Long> notTodoListIds);
    
    long countByNotTodoListIdAndIsCompliantTrue(Long notTodoListId);
    
    void deleteAllByNotTodoListIdIn(List<Long> notTodoListIds);
}
