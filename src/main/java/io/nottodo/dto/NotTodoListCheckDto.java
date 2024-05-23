package io.nottodo.dto;

import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.NotTodoListCheck;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NotTodoListCheckDto {
    
    
    private Long checkId;
    
    private Long notTodoListId; // NotTodoList의 ID만 저장
    private LocalDate checkDate;
    private boolean isCompliant;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    
    
    public static NotTodoListCheckDto createNotTodoListCheckDto(NotTodoListCheck check) {
        NotTodoListCheckDto notTodoListCheckDto = new NotTodoListCheckDto();
        notTodoListCheckDto.checkId = check.getCheckId();
        notTodoListCheckDto.notTodoListId = check.getNotTodoList().getId();
        notTodoListCheckDto.checkDate = check.getCheckDate();
        notTodoListCheckDto.isCompliant = check.isCompliant();
        notTodoListCheckDto.createAt = check.getCreateAt();
        notTodoListCheckDto.updateAt = check.getUpdateAt();
        return notTodoListCheckDto;
    }
}
