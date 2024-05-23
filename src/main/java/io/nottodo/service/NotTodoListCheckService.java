package io.nottodo.service;

import io.nottodo.dto.NotTodoListCheckDto;
import io.nottodo.dto.NotTodoListDto;
import io.nottodo.request.NotTodoCheckRequest;

import java.time.LocalDate;

public interface NotTodoListCheckService {
    
    NotTodoListCheckDto notTodoCheck(NotTodoCheckRequest notTodoCheckRequest);
    
    NotTodoListCheckDto updateNotTodoCheck(Long checkId, NotTodoCheckRequest request);
    
    NotTodoListDto deleteTodoChecksAndUpdateEndDate(Long todoListId, LocalDate deleteFromDate);
}
