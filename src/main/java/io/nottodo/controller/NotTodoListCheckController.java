package io.nottodo.controller;

import io.nottodo.dto.DailyComplianceDto;
import io.nottodo.dto.NotTodoListCheckDto;
import io.nottodo.request.NotTodoCheckRequest;
import io.nottodo.service.NotTodoListCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/not-todo-list-check")
public class NotTodoListCheckController {
    private final NotTodoListCheckService notTodoListCheckService;
    
    
    @PostMapping("")
    public NotTodoListCheckDto notTodoListCheck(@Validated @RequestBody NotTodoCheckRequest notTodoCheckRequest) {
        return notTodoListCheckService.notTodoCheck(notTodoCheckRequest);
    }
    
    @PatchMapping("")
    public NotTodoListCheckDto modifyNotTodoListCheck(@Validated @RequestBody NotTodoCheckRequest notTodoCheckRequest) {
        return null;
    }
 
    
    
}