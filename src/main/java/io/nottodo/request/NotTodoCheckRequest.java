package io.nottodo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotTodoCheckRequest {
    
    @NotNull(message = "notTodoList Id가 null 입니다.")
    private Long notTodoListId;
    @NotNull(message = "checkDate null 입니다.")
    private LocalDate checkDate;
    @NotNull(message = "체크 값이 null 입니다.")
    private Boolean isCompliant;
}
