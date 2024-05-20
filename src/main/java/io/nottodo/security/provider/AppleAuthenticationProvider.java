package io.nottodo.security.provider;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Claims;
import io.nottodo.context.MemberContext;
import io.nottodo.security.service.MemberDetailService;
import io.nottodo.security.token.AppleAuthenticationToken;
import io.nottodo.security.token.KakaoAuthenticationToken;
import io.nottodo.security.validation.AppleTokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component(value = "appleAuthenticationProvider")
@RequiredArgsConstructor
public class AppleAuthenticationProvider implements AuthenticationProvider {
    
    private final MemberDetailService memberDetailService;
    private final AppleTokenValidator appleTokenValidator;
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AppleAuthenticationToken token = (AppleAuthenticationToken) authentication;
        String idToken = (String) token.getPrincipal();
        String realName =  token.getName();
        
        if (appleTokenValidator.isValidAppleToken(idToken)) {
            
            Claims claims = appleTokenValidator.getUserInfoFromToken(idToken);
            String userId = claims.getSubject();  //
            
            MemberContext memberContext = (MemberContext) memberDetailService.loadUserByUsernameAndAppleInfo(userId,claims,realName);
            return new AppleAuthenticationToken(memberContext.getMemberDto(), null, memberContext.getAuthorities());
        }
        
        throw new AuthenticationException("Invalid Apple ID Token") {};
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return AppleAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
