package io.nottodo.dto;

import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.NotTodoListCheck;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NotTodoListCheckDto {
    
    
    private Long checkId;
    private Long notTodoListId; // NotTodoList의 ID만 저장
    private LocalDate checkDate;
    private String notTodoListContent;
    private boolean compliant;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    
    
    
    public static NotTodoListCheckDto createNotTodoListCheckDto(NotTodoListCheck check) {
        return new NotTodoListCheckDto(
                check.getCheckId(),
                check.getNotTodoList().getId(),
                check.getCheckDate(),
                check.getNotTodoList().getNotTodoListContent(),
                check.isCompliant(),
                check.getCreateAt(),
                check.getUpdateAt()
        );
    }
    // 필요한 생성자 추가
    public NotTodoListCheckDto(Long checkId, Long notTodoListId, LocalDate checkDate, String notTodoListContent, boolean compliant, LocalDateTime createAt, LocalDateTime updateAt) {
        this.checkId = checkId;
        this.notTodoListId = notTodoListId;
        this.checkDate = checkDate;
        this.notTodoListContent = notTodoListContent;
        this.compliant = compliant;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
    
}
