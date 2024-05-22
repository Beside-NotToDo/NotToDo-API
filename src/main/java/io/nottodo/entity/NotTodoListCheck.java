package io.nottodo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "not_todo_list_check")
public class NotTodoListCheck extends BaseDate{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "체크 기본키")
    @Column(name = "CHECK_ID")
    private Long checkId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOT_TODO_LIST_ID")
    private NotTodoList notTodoList;
    
    @Comment(value = "체크 날짜")
    @Column(name = "CHECK_DATE")
    private LocalDate checkDate;
    
    @Comment(value = "준수 여부")
    @Column(name = "IS_COMPLIANT")
    private boolean isCompliant;
    
    
    
}
