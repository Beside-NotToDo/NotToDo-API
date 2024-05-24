package io.nottodo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OverallStatisticsDto {
    private double averageScore;
    private List<NotTodoListCheckDto> checks;
    private LocalDate startDate;
    private LocalDate endDate;
}
