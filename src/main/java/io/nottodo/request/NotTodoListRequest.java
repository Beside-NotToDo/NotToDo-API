package io.nottodo.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class NotTodoListRequest {
    
    private String notTodoListContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long categoryId;

}
