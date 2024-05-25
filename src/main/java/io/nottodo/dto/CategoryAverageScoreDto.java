package io.nottodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAverageScoreDto {
    private Long categoryId;
    private String categoryName;
    private double averageScore;
}