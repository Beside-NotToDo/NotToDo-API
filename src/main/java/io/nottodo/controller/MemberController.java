package io.nottodo.controller;


import io.nottodo.entity.Member;
import io.nottodo.jwt.JwtUtil;
import io.nottodo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class MemberController {
    
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    
    @GetMapping("/delete")
    public Member test(@AuthenticationPrincipal Jwt jwt) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return memberService.deleteMember(memberId);
    }
}
