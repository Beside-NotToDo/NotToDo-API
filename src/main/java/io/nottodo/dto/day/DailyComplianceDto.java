package io.nottodo.dto.day;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DailyComplianceDto {
    
    private String date;
    private String complianceType;
    
    
    public DailyComplianceDto(String date, String complianceType) {
        this.date = date;
        this.complianceType = complianceType;
    }
}
