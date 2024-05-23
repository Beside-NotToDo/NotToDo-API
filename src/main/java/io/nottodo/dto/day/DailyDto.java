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
        private String notTodoListContent;
        private boolean checked;
        private Long categoryId;
        private Long period;
    }
}
