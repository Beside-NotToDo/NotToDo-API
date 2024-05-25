package io.nottodo.service;

import io.nottodo.dto.*;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    ThemeStatisticsDto getThemeStatistics(Long memberId, Long categoryId);
    
    List<CategoryAverageScoreDto> getCategoryAverageScores(Long memberId);
}
