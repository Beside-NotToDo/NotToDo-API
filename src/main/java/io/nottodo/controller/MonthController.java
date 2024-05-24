package io.nottodo.controller;

import io.nottodo.dto.day.DailyComplianceDto;
import io.nottodo.dto.day.MonthDto;
import io.nottodo.dto.day.WeekDto;
import io.nottodo.jwt.JwtUtil;
import io.nottodo.request.WeekDailyRequest;
import io.nottodo.service.MonthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping("/month")
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class MonthController {
    
    private final MonthService monthService;
    private final JwtUtil jwtUtil;
    
    // 특정 회원의 특정 월에 대한 일별 준수 비율을 반환하는 엔드포인트
    
    /**
     * @param yearMonth 연월
     * @return MonthDto 연월 데이터
     */
    @GetMapping("")
    public MonthDto getMonthAndDaily(@AuthenticationPrincipal Jwt jwt,
                                     @RequestParam String yearMonth) {
        YearMonth month = YearMonth.parse(yearMonth);
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return monthService.getMonthAndDaily(memberId, month);
    }
    
    /**
     * 특정 연월과 주차의 데이터를 반환하는 엔드포인트.
     *
     * @param weekDailyRequest 연 월
     * @return WeekDto 주차 데이터
     */
    @PostMapping("/weekly")
    public WeekDto getWeekAndDaily(@AuthenticationPrincipal Jwt jwt,
                                   @RequestBody WeekDailyRequest weekDailyRequest) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return monthService.getWeekAndDaily(memberId, weekDailyRequest.getMonth(), weekDailyRequest.getWeek());
    }
}