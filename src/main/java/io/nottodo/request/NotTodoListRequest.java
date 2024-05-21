package io.nottodo.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class NotTodoListRequest {
    
    private String notTodoListTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer categoryId;

}
