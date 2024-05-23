package io.nottodo.controller;


import io.nottodo.dto.NotTodoListCheckDto;
import io.nottodo.dto.NotTodoListDto;
import io.nottodo.request.NotTodoCheckRequest;
import io.nottodo.service.NotTodoListCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/not-todo-list-check")
@ResponseStatus(HttpStatus.OK)
public class NotTodoListCheckController {
    private final NotTodoListCheckService notTodoListCheckService;
    
    
    @PostMapping("")
    public NotTodoListCheckDto notTodoListCheck(@Validated @RequestBody NotTodoCheckRequest notTodoCheckRequest) {
        return notTodoListCheckService.notTodoCheck(notTodoCheckRequest);
    }
    
    @PatchMapping("/{checkId}")
    public NotTodoListCheckDto modifyNotTodoListCheck(@PathVariable("checkId") Long checkId, @Validated @RequestBody NotTodoCheckRequest notTodoCheckRequest) {
        return notTodoListCheckService.updateNotTodoCheck(checkId,notTodoCheckRequest);
    }
    
    @DeleteMapping("/{notTodoListId}")
    public NotTodoListDto deleteNotTodoCheckAndUpdateEndDate(@PathVariable("notTodoListId") Long notTodoListId, @RequestParam LocalDate deleteDate) {
        return notTodoListCheckService.deleteTodoChecksAndUpdateEndDate(notTodoListId, deleteDate);
    }
    
    
    
}