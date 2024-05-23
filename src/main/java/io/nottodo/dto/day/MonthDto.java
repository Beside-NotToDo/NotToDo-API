package io.nottodo.dto.day;

import lombok.Data;

import java.util.List;

@Data
public class MonthDto {
    private HeaderDto header;
    private List<DailyDto> daily; // List<DailyDto> 타입으로 변경
}