package io.nottodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotTodoListWithChecksDto {
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
    private List<NotTodoListCheckDto> notTodoListChecks;
}
