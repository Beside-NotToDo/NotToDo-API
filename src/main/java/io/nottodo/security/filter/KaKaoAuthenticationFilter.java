package io.nottodo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nottodo.dto.MemberDto;
import io.nottodo.request.KaKaoLoginRequest;
import io.nottodo.security.token.KakaoAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Slf4j
public class KaKaoAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
   
    public KaKaoAuthenticationFilter( ) {
        super(new AntPathRequestMatcher("/kakao/login", "POST"));
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("KaKaoAuthenticationFilter 진입");
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            throw new IllegalArgumentException("Post 요청이 아닙니다.");
        }
        
        
        KaKaoLoginRequest loginRequest = objectMapper.readValue(request.getReader(), KaKaoLoginRequest.class);
        String kakaoAccessToken = loginRequest.getAccessToken();
        //String kakaoAccessToken = memberDto.getKakaoAccessToken();
        if (kakaoAccessToken == null || kakaoAccessToken.isEmpty()) {
            throw new IllegalArgumentException("카카오 엑세스 토큰이 없습니다.");
        }
        
        KakaoAuthenticationToken authRequest = new KakaoAuthenticationToken(kakaoAccessToken, null);
        
        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
 
    
}
