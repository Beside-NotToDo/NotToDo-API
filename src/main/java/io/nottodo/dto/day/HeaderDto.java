package io.nottodo.dto.day;

import lombok.Data;

import java.util.List;

@Data
public class HeaderDto {
    private String month;
    private List<WeeklyDto> weekly;
    
    @Data
    public static class WeeklyDto {
        private String date;
        private String weekDay;
        private String complianceType;
    }
}