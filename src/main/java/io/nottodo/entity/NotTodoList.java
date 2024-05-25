package io.nottodo.entity;


import io.nottodo.request.NotTodoListTemporaryStorageRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "not_todo_list")
@Getter
public class NotTodoList extends BaseDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "낫 투두 리스트 기본키")
    @Column(name = "NOT_TODO_LIST_ID")
    private Long id;
    
    @Comment(value = "낫 투두 리스트 내용")
    @Column(name = "NOT_TODO_LIST_CONTENT")
    private String notTodoListContent;
    
    @Comment(value = "시작 일자")
    @Column(name = "START_DATE")
    private LocalDate startDate;
    
    @Column(name = "END_DATE")
    private LocalDate endDate;
    
    @Comment(value = "삭제 여부")
    @Column(name = "TEMPORARY_STORAGE")
    private Boolean temporaryStorage;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment(value = "회원 기본키")
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment(value = "카테고리 기본키")
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    
    
    @OneToMany(mappedBy = "notTodoList" , cascade = CascadeType.REMOVE,orphanRemoval = true)
    @ToString.Exclude
    private List<NotTodoListCheck> notTodoListCheck = new ArrayList<>();
    
    
    public static NotTodoList createNotTodoEntity(String notTodoListContent, LocalDate startDate, LocalDate endDate) {
        NotTodoList notTodoList = new NotTodoList();
        notTodoList.notTodoListContent = notTodoListContent;
        notTodoList.startDate = startDate;
        notTodoList.endDate = endDate;
        return notTodoList;
    }
    
    
    public void updateNotTodoEntity(String content, LocalDate startDate, LocalDate endDate) {
        this.notTodoListContent = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    /**
     * 연관 관계 편의 메서드
     *
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
     *
     * @param category
     */
    public void setCategory(Category category) {
        if (this.category != null) {
            this.category.getNotTodoLists().remove(this);
        }
        this.category = category;
        category.getNotTodoLists().add(this);
    }
    
    /**
     * EndDate를 수정
     *
     * @param updateDate
     */
    public void modifyEndDate(LocalDate updateDate) {
        this.endDate = updateDate;
    }
    
    public void updateNotTodoTemporaryStorage(NotTodoListTemporaryStorageRequest notTodoListTemporaryStorageRequest) {
        this.notTodoListContent = notTodoListTemporaryStorageRequest.getNotTodoListContent();
        this.startDate = notTodoListTemporaryStorageRequest.getStartDate();
        this.endDate = notTodoListTemporaryStorageRequest.getEndDate();
        this.temporaryStorage = notTodoListTemporaryStorageRequest.getTemporaryStorage();
    }
}


