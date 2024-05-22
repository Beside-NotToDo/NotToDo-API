package io.nottodo.controller;


import io.nottodo.dto.NotTodoListDto;
import io.nottodo.exception.InvalidDateRangeException;
import io.nottodo.jwt.JwtUtil;
import io.nottodo.request.NotTodoListRequest;
import io.nottodo.service.NotTodoListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/not-todo-lists")
@Slf4j
@ResponseStatus(HttpStatus.OK)
public class NotTodoListController {
    private final NotTodoListService notTodoListService;
    private final JwtUtil jwtUtil;
    
    // 조회 - 전체 조회
    @GetMapping("")
    public List<NotTodoListDto> getAllNotTodoLists(@AuthenticationPrincipal Jwt jwt) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return notTodoListService.getNotTodoList(memberId);
    }
    
    // 조회 - 하나 조회
    @GetMapping("/{id}")
    public NotTodoListDto getNotTodoList(@PathVariable("id") Long id, @AuthenticationPrincipal Jwt jwt) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return notTodoListService.getNotTodoList(id, memberId);
    }
    
    //작성
    @PostMapping("")
    public Long createNotTodoList(@Validated @RequestBody NotTodoListRequest notTodoListRequest, @AuthenticationPrincipal Jwt jwt) {
        // 날짜 검증
        if (notTodoListRequest.getEndDate().isBefore(notTodoListRequest.getStartDate())) {
            throw new InvalidDateRangeException("종료 날짜는 시작 날짜보다 이후여야 합니다");
        }
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return notTodoListService.createNotTodoList(notTodoListRequest, memberId);
        
    }
    //수정
    @PatchMapping("/{id}")
    public Long updateNotTodoList(@PathVariable("id") Long id, @Validated @RequestBody NotTodoListRequest notTodoListRequest, @AuthenticationPrincipal Jwt jwt) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        
        if (notTodoListRequest.getEndDate().isBefore(notTodoListRequest.getStartDate())) {
            throw new InvalidDateRangeException("종료 날짜는 시작 날짜보다 이후여야 합니다");
        }
        return notTodoListService.modifyNotTodoList(id, notTodoListRequest, memberId);
    }
    
    
    // 삭제
    @DeleteMapping("/{id}")
    public Long deleteNotTodoList(@PathVariable("id") Long id, @AuthenticationPrincipal Jwt jwt) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return notTodoListService.deleteNotTodoList(id, memberId);
    }
    
    
}
