package io.nottodo.dto;

import java.time.LocalDate;

public class DailyComplianceDto {
    
    private LocalDate date;
    private int complianceRate;
    
    public DailyComplianceDto(LocalDate date, int complianceRate) {
        this.date = date;
        this.complianceRate = complianceRate;
    }
}
