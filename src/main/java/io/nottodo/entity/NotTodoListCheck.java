package io.nottodo.entity;

import io.nottodo.request.NotTodoCheckRequest;
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
    
    
    public static NotTodoListCheck createNotTodoCheck(NotTodoCheckRequest notTodoCheckRequest) {
        NotTodoListCheck notTodoListCheck = new NotTodoListCheck();
        notTodoListCheck.checkDate = notTodoCheckRequest.getCheckDate();
        notTodoListCheck.isCompliant = notTodoCheckRequest.getIsCompliant();
        return notTodoListCheck;
    }
    
    /**
     * 연관 관계 편의메서드
     * @param notTodoList
     */
    public void setNotTodoList(NotTodoList notTodoList) {
        if (this.notTodoList != null) {
            this.notTodoList.getNotTodoListCheck().remove(this);
        } 
        this.notTodoList = notTodoList;
        notTodoList.getNotTodoListCheck().add(this);
    }
    
   
}
