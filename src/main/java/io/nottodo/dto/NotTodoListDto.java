package io.nottodo.dto;


import io.nottodo.entity.Category;
import io.nottodo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NotTodoListDto {
    
    
    private Long notTodoListId;
    private String notTodoListContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long memberId;
    private String username;
    private String memberName;
    private Long categoryId;
    private String categoryName;
    
    public NotTodoListDto(Long notTodoListId, String notTodoListContent, LocalDate startDate, LocalDate endDate) {
        this.notTodoListId = notTodoListId;
        this.notTodoListContent = notTodoListContent;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}


