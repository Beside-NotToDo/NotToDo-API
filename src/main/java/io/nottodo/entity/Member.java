package io.nottodo.entity;


import io.nottodo.login.LoginType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "회원 기본키")
    @Column(name = "MEMBER_ID")
    private Long id;
    
    @Comment(value = "로그인아이디")
    @Column(name = "USERNAME")
    private String username;
    
    @Comment(value = "비밀번호")
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "MEMBER_LOGIN_TYPE")
    @Comment(value = "회원 로그인 타입")
    @Enumerated(EnumType.STRING)
    private LoginType loginType;
    
    @CreatedBy
    @Comment(value = "가입일시")
    @Column(name = "JOIN_DATE")
    private LocalDateTime joinDate;
    
    @LastModifiedBy
    @Comment(value = "수정일시")
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    
    
    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<MemberRole> memberRoles = new ArrayList<>();
    
    
    public void passSetting(String password) {
        this.password = password;
    }
    
    public Member(String username, String password, LoginType loginType) {
        this.username = username;
        this.password = password;
        this.loginType = loginType;
    }
}
