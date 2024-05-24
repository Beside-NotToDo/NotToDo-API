package io.nottodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeStatisticsDto {
    private Long categoryId;
    private double averageScore;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<NotTodoListContentDto> contents;
    private List<String> excludeFromCounting; // 제외된 항목
    private List<String> reason; // 제외된 이유
}
