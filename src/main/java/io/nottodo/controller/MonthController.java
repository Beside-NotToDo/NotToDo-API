package io.nottodo.controller;

import io.nottodo.dto.DailyComplianceDto;
import io.nottodo.service.MonthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/month")
@RequiredArgsConstructor
public class MonthController {
    
    private final MonthService monthService;
    
    
    // 특정 회원의 특정 월에 대한 일별 준수 비율을 반환하는 엔드포인트
    @GetMapping("/monthly-compliance")
    public List<DailyComplianceDto> getMonthlyCompliance(@RequestParam Long memberId,
                                                         @RequestParam String yearMonth) {
        YearMonth month = YearMonth.parse(yearMonth);
        return monthService.getMonthlyCompliance(memberId, month);
    }
}
