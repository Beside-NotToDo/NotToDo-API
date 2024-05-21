package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "member_role")
public class MemberRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "회원 권한 기본키")
    @Column(name = "MEMBER_ROLE_ID")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment(value = "회원 기본키")
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment(value = "권한 기본키")
    @JoinColumn(name = "ROLE_ID")
    private Role role;
    
    
    
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getMemberRoles().remove(this);
        }
        this.member = member;
        member.getMemberRoles().add(this);
    }
    
    public void setRole(Role role) {
        if (this.role != null) {
            this.role.getMemberRoles().remove(this);
        }
        this.role = role;
        role.getMemberRoles().add(this);
    }
}
