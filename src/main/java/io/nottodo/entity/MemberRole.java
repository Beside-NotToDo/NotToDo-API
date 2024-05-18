package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MemberRole extends BaseDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_role_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
    

    
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
