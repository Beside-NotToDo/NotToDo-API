package io.nottodo.service;

import io.nottodo.dto.NotTodoListDto;
import io.nottodo.request.NotTodoListRequest;

import java.util.List;

public interface NotTodoListService {
    
    // 조회
    
     List<NotTodoListDto> getNotTodoList(Long memberId);
    
     NotTodoListDto getNotTodoList(Long id, Long memberId);
     
    // 작성
    Long createNotTodoList(NotTodoListRequest notTodoListRequest , Long memberId);
    
    // 수정
    Long modifyNotTodoList(Long id,  NotTodoListRequest notTodoListRequest, Long memberId);
    // 삭제
    
    Long deleteNotTodoList(Long id, Long memberId);
}
