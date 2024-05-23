package io.nottodo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WeekDailyRequest {
    
    
    @NotNull(message = "month가 Null입니다")
    private Integer month;
    @NotNull(message = "week가 Null입니다")
    private Integer week;
    
}
