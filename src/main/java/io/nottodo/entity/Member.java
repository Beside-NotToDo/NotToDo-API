package io.nottodo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseDate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    
    @Comment(value = "로그인 아이디")
    private String username;
    @Comment(value = "비밀 번호")
    private String password;
    
    
    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<MemberRole> memberRoles = new ArrayList<>();
    
    
 
    public void passSetting(String password) {
        this.password = password;
    }
}
