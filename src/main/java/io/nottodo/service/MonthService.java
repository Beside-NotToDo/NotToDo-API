package io.nottodo.service;

import io.nottodo.dto.day.MonthDto;
import io.nottodo.dto.day.WeekDto;

import java.time.LocalDate;
import java.time.YearMonth;

public interface MonthService {
    MonthDto getMonthAndDaily(Long memberId, YearMonth yearMonth);
    
    WeekDto getWeekAndDaily(Long memberId, Integer month, Integer week);
    
}
