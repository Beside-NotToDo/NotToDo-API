package io.nottodo.dto;

import io.nottodo.entity.Member;
import io.nottodo.login.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    
    private Long id;
    private String username;
    private String memberName;
    private String password;
    private LoginType loginType;
    private LocalDateTime joinDate;
    private LocalDateTime updateDate;
    private List<String> memberRoles;
    
    public static MemberDto createMemberDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.id = member.getId();
        memberDto.username = member.getUsername();
        memberDto.memberName = member.getMemberName();
        memberDto.password = member.getPassword();
        memberDto.loginType = member.getLoginType();
        memberDto.joinDate = member.getJoinDate();
        memberDto.updateDate = member.getUpdateDate();
        memberDto.memberRoles = member.getMemberRoles().stream()
                .map(r -> r.getRole().getRoleName()).collect(Collectors.toList());
        return memberDto;
    }
}
