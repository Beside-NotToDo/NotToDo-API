package io.nottodo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MemberController {
    
    
    @GetMapping("/user")
    public Jwt test(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }
}
