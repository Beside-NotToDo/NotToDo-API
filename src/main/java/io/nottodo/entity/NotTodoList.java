package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Table(name = "not_todo_list")
@Getter
public class NotTodoList extends BaseDate{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "낫 투두 리스트 기본키")
    @Column(name = "NOT_TODO_LIST_ID")
    private Integer id;
    
    @Comment(value = "낫 투두 리스트 제목")
    @Column(name = "NOT_TODO_LIST_TITLE")
    private String notTodoListTitle;
    
    @Comment(value = "시작 일자")
    @Column(name = "START_DATE")
    private LocalDate startDate;
    
    @Column(name = "END_DATE")
    private LocalDate endDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment(value = "회원 기본키")
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment(value = "카테고리 기본키")
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    
    /**
     * 연관 관계 편의 메서드
     * @param member
     */
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getNotTodoLists().remove(this);
        }
        this.member = member;
        member.getNotTodoLists().add(this);
    }
    
    /**
     * 연관 관계 편의 메서드
     * @param category
     */
    public void setCategory(Category category) {
        if (this.category != null) {
            this.category.getNotTodoLists().remove(this);
        }
        this.category = category;
        category.getNotTodoLists().add(this);
    }
}


