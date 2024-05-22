package io.nottodo.dto;

import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.NotTodoListCheck;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NotTodoListCheckDto {
    
    
    private Long checkId;
    private NotTodoList notTodoList;
    private LocalDate checkDate;
    private boolean isCompliant;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    
    
    public static NotTodoListCheckDto createNotTodoListCheckDto(NotTodoListCheck check) {
        NotTodoListCheckDto notTodoListCheckDto = new NotTodoListCheckDto();
        notTodoListCheckDto.checkId = check.getCheckId();
        notTodoListCheckDto.notTodoList = check.getNotTodoList();
        notTodoListCheckDto.checkDate = check.getCheckDate();
        notTodoListCheckDto.isCompliant = check.isCompliant();
        notTodoListCheckDto.createAt =  check.getCreateAt();
        notTodoListCheckDto.updateAt = check.getUpdateAt();
        return notTodoListCheckDto;
    }
}
