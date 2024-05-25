package io.nottodo.dto.day;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DailyDto {
    private String date;
    private List<TodoDto> todos;
    
    @Data
    public static class TodoDto {
        private Long notTodoListId;
        private String notTodoListContent;
        private Long checkId;
        private boolean checked;
        private Long categoryId;
        private Long period;
    }
}
