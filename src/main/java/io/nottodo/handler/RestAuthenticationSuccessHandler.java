package io.nottodo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.nottodo.dto.MemberDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component(value = "restAuthenticationSuccessHandler")
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        MemberDto memberDto = (MemberDto) authentication.getPrincipal();
        memberDto.setPassword(null);
        mapper.writeValue(response.getWriter(),memberDto);
        clearAuthenticationAttributes(request);
    }
    
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); // 마지막 예외 지우기
    }
}
