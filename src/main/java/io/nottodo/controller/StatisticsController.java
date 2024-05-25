package io.nottodo.controller;

import io.nottodo.dto.CategoryAverageScoreDto;
import io.nottodo.dto.NotTodoListWithChecksDto;
import io.nottodo.dto.OverallStatisticsDto;
import io.nottodo.dto.ThemeStatisticsDto;
import io.nottodo.jwt.JwtUtil;
import io.nottodo.request.ThemeStatisticsRequest;
import io.nottodo.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class StatisticsController {
    
    
    private final StatisticsService statisticsService;
    private final JwtUtil jwtUtil;
    
    
    /**
     * 카테고리 통계 정보를 조회합니다.
     *
     * @param jwt 사용자의 인증 정보를 포함하는 JWT 토큰
     * @param request 카테고리 ID 및 필요한 정보를 포함하는 요청 객체
     * @return 카테고리 통계 정보
     */
    @PostMapping("/category")
    public ResponseEntity<ThemeStatisticsDto> getCategoryStatistics(@AuthenticationPrincipal Jwt jwt,
                                                                    @RequestBody ThemeStatisticsRequest request) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        ThemeStatisticsDto categoryStatistics = statisticsService.getThemeStatistics(memberId, request.getCategoryId());
        return ResponseEntity.ok(categoryStatistics);
    }
    
    /**
     * 사용자별 카테고리별 평균 점수 조회
     *
     * @param jwt 사용자의 인증 정보를 포함하는 JWT 토큰
     * @return 카테고리별 평균 점수
     */
    @GetMapping("/category/averages")
    public ResponseEntity<List<CategoryAverageScoreDto>> getCategoryAverageScoresByUser(@AuthenticationPrincipal Jwt jwt) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return ResponseEntity.ok(statisticsService.getCategoryAverageScores(memberId));
    }
 
}


