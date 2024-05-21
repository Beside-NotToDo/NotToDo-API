package io.nottodo.controller;


import io.nottodo.dto.MemberDto;
import io.nottodo.dto.NotTodoListDto;
import io.nottodo.entity.Member;
import io.nottodo.jwt.JwtUtil;
import io.nottodo.request.NotTodoListRequest;
import io.nottodo.service.NotTodoListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/not-todo-lists")
public class NotTodoListController {
    private final NotTodoListService notTodoListService;
    private final JwtUtil jwtUtil;
    
    // 조회 - 전체 조회
    @GetMapping
    public List<NotTodoListDto> getAllNotTodoLists(@AuthenticationPrincipal Jwt jwt) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return notTodoListService.getNotTodoList(memberId);
    }
    //작성
    @PostMapping
    public Long createNotTodoList(@Validated @RequestBody NotTodoListRequest notTodoListRequest, @AuthenticationPrincipal Jwt jwt) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return notTodoListService.createNotTodoList(notTodoListRequest, memberId);
        
    }
    //수정
    @PutMapping("/{id}")
    public Long updateNotTodoList(@PathVariable("id") Long id, @Valid @RequestBody NotTodoListRequest notTodoListRequest, @AuthenticationPrincipal Jwt jwt) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return notTodoListService.modifyNotTodoList(id, notTodoListRequest, memberId);
    }
    
    
    //삭제
    
    
}
