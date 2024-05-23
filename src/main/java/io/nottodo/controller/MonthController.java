package io.nottodo.controller;

import io.nottodo.dto.day.DailyComplianceDto;
import io.nottodo.dto.day.MonthDto;
import io.nottodo.dto.day.WeekDto;
import io.nottodo.jwt.JwtUtil;
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
     * @param month 월
     * @param week  주차
     * @return WeekDto 주차 데이터
     */
    @GetMapping("/weekly")
    public WeekDto getWeekAndDaily(@AuthenticationPrincipal Jwt jwt,
                                   @RequestParam Integer month,
                                   @RequestParam Integer week) {
        Long memberId = Long.valueOf(jwtUtil.extractClaim(jwt, "id"));
        return monthService.getWeekAndDaily(memberId, month, week);
    }
    
    private LocalDate getStartOfWeek(YearMonth yearMonth, int week) {
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate firstSunday = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        
        // 첫 주는 1일부터 시작
        if (week == 0) {
            return firstDayOfMonth;
        }
        
        // 첫 주 이후는 일요일을 기준으로 계산
        return firstSunday.plusWeeks(week);
    }
    
    
    private LocalDate getEndOfWeek(LocalDate startOfWeek, YearMonth yearMonth) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        
        // 주의 종료일이 요청된 월을 넘어가지 않도록 조정
        if (startOfWeek.getMonthValue() != yearMonth.getMonthValue()) {
            startOfWeek = yearMonth.atDay(1);
        }
        
        if (endOfWeek.getMonthValue() != yearMonth.getMonthValue()) {
            endOfWeek = yearMonth.atEndOfMonth();
        }
        
        // 첫 주 거나 마지막째 주일 경우 시작일이 월의 첫 날 이후인 경우에만 종료일 조정
//        if (yearMonth.atDay(1).getDayOfWeek() != DayOfWeek.SUNDAY && startOfWeek.isEqual(yearMonth.atDay(1))) {
//            endOfWeek = yearMonth.atDay(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
//        } 잠시 주석
        
        return endOfWeek;
    }
}