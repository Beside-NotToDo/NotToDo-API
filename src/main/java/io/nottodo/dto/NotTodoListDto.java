package io.nottodo.dto;


import io.nottodo.entity.Category;
import io.nottodo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @ToString.Exclude
    private List<NotTodoListCheckDto> notTodoListCheck;
    
 
    
    public NotTodoListDto(Long notTodoListId, String notTodoListContent, LocalDate startDate, LocalDate endDate, Long memberId, String username, String memberName, Long categoryId, String categoryName) {
        this.notTodoListId = notTodoListId;
        this.notTodoListContent = notTodoListContent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberId = memberId;
        this.username = username;
        this.memberName = memberName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}


