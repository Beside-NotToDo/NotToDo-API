package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "category")
@Getter
public class NotTodoList extends BaseDate{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "낫 투두 리스트 기본키")
    @Column(name = "NOT_TODO_LIST_ID")
    private Integer id;
    
    
    
    @ManyToOne
    @Comment(value = "회원 기본키")
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    
    
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getNotTodoLists().remove(this);
        }
        this.member = member;
        member.getNotTodoLists().add(this);
    }
}


