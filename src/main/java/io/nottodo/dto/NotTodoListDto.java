package io.nottodo.dto;


import io.nottodo.entity.Category;
import io.nottodo.entity.Member;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotTodoListDto {
    
    
    private Long id;
    private String notTodoListTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private Member member;
    private Category category;
    
    
}


