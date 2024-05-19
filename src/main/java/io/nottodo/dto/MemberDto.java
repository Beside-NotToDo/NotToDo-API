package io.nottodo.dto;

import io.nottodo.login.LoginType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Data
public class MemberDto {
    
    private Long id;
    private String username;
    private String password;
    private LoginType loginType;
    private LocalDateTime joinDate;
    private LocalDateTime updateDate;
    
    private String kakaoAccessToken; // 카카오 토큰
}
