package io.nottodo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nottodo.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component(value = "restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "인증이 되지 않았습니다.");
        errorResponse.addValidation("AuthenticationEntryPoint", authException.getMessage());
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
