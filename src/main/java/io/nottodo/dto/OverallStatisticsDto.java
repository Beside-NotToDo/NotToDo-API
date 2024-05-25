package io.nottodo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OverallStatisticsDto {
    private int averageScore; // 평균 점수를 정수형으로 변경합니다.
    private LocalDate startDate;
    private LocalDate endDate;
    private List<NotTodoListWithChecksDto> contents; // NotTodoListWithChecksDto 리스트를 추가합니다.
}
