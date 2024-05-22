package io.nottodo.service;

import io.nottodo.dto.DailyComplianceDto;
import io.nottodo.dto.NotTodoListCheckDto;
import io.nottodo.request.NotTodoCheckRequest;

import java.time.YearMonth;
import java.util.List;

public interface NotTodoListCheckService {
    
    NotTodoListCheckDto notTodoCheck(NotTodoCheckRequest notTodoCheckRequest);
}
