package io.nottodo.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotTodoListTemporaryStorageRequest {
    
    
    @NotBlank(message = "내용은 비어 있을 수 없습니다")
    private String notTodoListContent;
    
    @NotNull(message = "시작 날짜는 비어 있을 수 없습니다")
    @FutureOrPresent(message = "시작 날짜는 현재 또는 미래여야 합니다")
    private LocalDate startDate;
    
    @NotNull(message = "종료 날짜는 비어 있을 수 없습니다")
    @FutureOrPresent(message = "종료 날짜는 현재 또는 미래여야 합니다")
    private LocalDate endDate;
    
    @NotNull(message = "카테고리 ID는 비어 있을 수 없습니다")
    private Long categoryId;
    
    @NotNull(message = "삭제 여부 체크 값이 올바르지 않습니다")
    private Boolean temporaryStorage;
    
}
