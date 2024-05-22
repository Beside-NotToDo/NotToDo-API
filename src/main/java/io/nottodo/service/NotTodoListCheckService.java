package io.nottodo.service;

import io.nottodo.dto.DailyComplianceDto;

import java.time.YearMonth;
import java.util.List;

public interface NotTodoListCheckService {
     List<DailyComplianceDto> getMonthlyCompliance(Long memberId, YearMonth yearMonth);
}
