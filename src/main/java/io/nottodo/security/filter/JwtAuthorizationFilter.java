package io.nottodo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nottodo.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
  
    private  ObjectMapper objectMapper = new ObjectMapper();
    
    //
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        List<String> urlList = List.of("/kakao/login", "/apple/login","/api-docs.html", "/v3/api-docs", "/swagger-ui");
        String requestURI = request.getRequestURI();
       
        for (String url : urlList) {
            if (requestURI.startsWith(url)) {
               filterChain.doFilter(request,response);
               return;
            }
        }
        String token = request.getHeader("rAuthorization");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (token == null || !token.startsWith("Bearer ")) {
            ErrorResponse errorResponse = new ErrorResponse("401", "Http 헤더에 Jwt 토큰이 없습니다.");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }
        filterChain.doFilter(request, response);
    
        
     
    }
}
