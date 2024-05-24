package io.nottodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotTodoListContentDto {
    private Long notTodoListId;
    private String notTodoListContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<NotTodoListCheckDto> checks;
    
}
