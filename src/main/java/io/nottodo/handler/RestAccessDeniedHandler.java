package io.nottodo.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.nottodo.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component(value = "restAccessDeniedHandler")
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.FORBIDDEN.value()), "권한 없음");
        errorResponse.addValidation("AccessDenied",accessDeniedException.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
