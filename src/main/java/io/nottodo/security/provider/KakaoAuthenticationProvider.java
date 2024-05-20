package io.nottodo.security.provider;

import com.fasterxml.jackson.databind.JsonNode;
import io.nottodo.context.MemberContext;
import io.nottodo.security.service.MemberDetailService;
import io.nottodo.security.token.KakaoAuthenticationToken;
import io.nottodo.security.validation.KakaoTokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(value = "kakaoAuthenticationProvider")
public class KakaoAuthenticationProvider implements AuthenticationProvider {
    
    private final MemberDetailService memberDetailsService;
    private final KakaoTokenValidator kakaoTokenValidator;
  
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KakaoAuthenticationToken token = (KakaoAuthenticationToken) authentication;// 엑세스토큰
        String kakaoAccessToken = (String) token.getPrincipal();
        
        // 카카오 액세스 토큰을 검증하는 로직 추가
        if (kakaoTokenValidator.isValidKakaoToken(kakaoAccessToken)) {
            // 유효한 토큰인 경우 권한을 설정하여 인증 객체 반환
            JsonNode kakaoUserInfo = kakaoTokenValidator.getUserInfo(kakaoAccessToken);
            String username =  kakaoUserInfo.get("id").asText();
            MemberContext memberContext = (MemberContext) memberDetailsService.loadUserByUsernameAndKakaoInfo(username,kakaoUserInfo);
            
            return new KakaoAuthenticationToken(memberContext.getMemberDto(), null, memberContext.getAuthorities());
        } else {
            throw new AuthenticationException("Invalid Kakao Access Token") {};
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return KakaoAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
