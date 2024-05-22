package io.nottodo.service.impl;

import io.nottodo.dto.NotTodoListCheckDto;
import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.NotTodoListCheck;
import io.nottodo.repository.NotTodoListCheckRepository;
import io.nottodo.repository.NotTodoListRepository;
import io.nottodo.request.NotTodoCheckRequest;
import io.nottodo.service.NotTodoListCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotTodoListCheckServiceImpl implements NotTodoListCheckService{
    
    private final NotTodoListRepository notTodoListRepository;
    private final NotTodoListCheckRepository notTodoListCheckRepository;
    
    
    @Override
    @Transactional
    public NotTodoListCheckDto notTodoCheck(NotTodoCheckRequest notTodoCheckRequest) {
        NotTodoList notTodoList = notTodoListRepository.findById(notTodoCheckRequest.getNotTodoListId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Not Todo"));
        NotTodoListCheck notTodoCheck = NotTodoListCheck.createNotTodoCheck(notTodoCheckRequest);
        notTodoCheck.setNotTodoList(notTodoList);
        NotTodoListCheck saveNotTodo = notTodoListCheckRepository.save(notTodoCheck);
        return NotTodoListCheckDto.createNotTodoListCheckDto(saveNotTodo);
    }
}