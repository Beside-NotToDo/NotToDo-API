package io.nottodo.jwt;

import io.nottodo.dto.MemberDto;
import io.nottodo.login.LoginType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    public String extractClaim(Jwt jwt, String claim) {
        return jwt.getClaimAsString(claim);
    }
    
    public MemberDto extractMemberDetails(Jwt jwt) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(Long.valueOf(jwt.getClaimAsString("id")));
        memberDto.setUsername(jwt.getClaimAsString("username"));
        memberDto.setMemberName(jwt.getClaimAsString("memberName"));
        
        // memberLoginType을 enum으로 변환하여 설정
        String loginTypeString = jwt.getClaimAsString("memberLoginType");
        // 또는 기본값 설정
        memberDto.setLoginType(loginTypeString != null ? LoginType.valueOf(loginTypeString) : null);
        
        memberDto.setMemberRoles(jwt.getClaimAsStringList("authority"));
        return memberDto;
    }
}
