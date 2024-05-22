package io.nottodo.repository;

import io.nottodo.entity.NotTodoListCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface NotTodoListCheckRepository extends JpaRepository<NotTodoListCheck, Long> {
    Optional<NotTodoListCheck> findByNotTodoListIdAndCheckDate(Long notTodoListId, LocalDate checkDate);
}
