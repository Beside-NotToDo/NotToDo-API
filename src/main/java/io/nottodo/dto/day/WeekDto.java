package io.nottodo.dto.day;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeekDto {
    private String startDate;
    private String endDate;
    private HeaderDto header;
    private List<DailyDto> daily;
}
