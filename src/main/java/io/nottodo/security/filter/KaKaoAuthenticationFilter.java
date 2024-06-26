package io.nottodo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import io.nottodo.dto.MemberDto;
import io.nottodo.login.LoginType;
import io.nottodo.request.KaKaoLoginRequest;
import io.nottodo.response.ErrorResponse;
import io.nottodo.security.token.KakaoAuthenticationToken;
import io.nottodo.signature.MacSecuritySigner;
import io.nottodo.signature.SecuritySigner;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class KaKaoAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JWK jwk;
    private final SecuritySigner securitySigner;
    
    public KaKaoAuthenticationFilter(SecuritySigner securitySigner, JWK jwk) {
        super(new AntPathRequestMatcher("/kakao/login", "POST"));
        this.securitySigner = securitySigner;
        this.jwk = jwk;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        log.info("KaKaoAuthenticationFilter 진입");
        
        try {
            if (!HttpMethod.POST.name().equals(request.getMethod())) {
                throw new IllegalArgumentException("Post 요청이 아닙니다.");
            }
            
            KaKaoLoginRequest loginRequest = objectMapper.readValue(request.getReader(), KaKaoLoginRequest.class);
            String kakaoAccessToken = loginRequest.getAccessToken();
            if (kakaoAccessToken == null || kakaoAccessToken.isEmpty()) {
                throw new IllegalArgumentException("카카오 엑세스 토큰이 없습니다.");
            }
            
            KakaoAuthenticationToken authRequest = new KakaoAuthenticationToken(kakaoAccessToken, null);
            return this.getAuthenticationManager().authenticate(authRequest);
            
        } catch (IllegalArgumentException e) {
            handleException(response, HttpServletResponse.SC_BAD_REQUEST, "400", e.getMessage(), "method", "accessToken");
            return null;
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        MemberDto user = (MemberDto) authResult.getPrincipal();
        String jwtToken;
        
        try {
            jwtToken = securitySigner.getToken(user, jwk);
            response.addHeader("Authorization", "Bearer " + jwtToken);
            
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("token", jwtToken);
            responseBody.put("id", String.valueOf(user.getId()));
            responseBody.put("username", user.getUsername());
            responseBody.put("memberName", user.getMemberName());
            responseBody.put("memberLoginType", user.getLoginType().name());
            responseBody.put("authority", String.join(",", user.getMemberRoles()));
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        } catch (JOSEException e) {
            handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500", "Internal Server Error", "tokenGeneration");
        }
    }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.error("KaKaoAuthenticationFilter 인증 실패: {}", failed.getMessage());
        
        handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "401", "Authentication failed", "authentication");
    }
    
    private void handleException(HttpServletResponse response, int status, String code, String message, String... fields) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ErrorResponse errorResponse = new ErrorResponse(code, message);
        
        for (String field : fields) {
            errorResponse.addValidation(field, message);
        }
        
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
