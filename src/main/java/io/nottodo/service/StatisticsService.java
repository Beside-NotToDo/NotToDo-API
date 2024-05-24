package io.nottodo.service;

import io.nottodo.dto.NotTodoListDto;
import io.nottodo.dto.NotTodoListWithChecksDto;
import io.nottodo.dto.OverallStatisticsDto;
import io.nottodo.dto.ThemeStatisticsDto;

import java.util.List;

public interface StatisticsService {
    ThemeStatisticsDto getThemeStatistics(Long memberId, Long categoryId);
    OverallStatisticsDto getOverallStatistics(Long memberId);
    List<NotTodoListWithChecksDto> getNotTodoListsByCategoryWithChecks(Long memberId, Long categoryId);
}
