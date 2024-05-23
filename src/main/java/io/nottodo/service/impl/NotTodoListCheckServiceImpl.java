package io.nottodo.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.nottodo.dto.NotTodoListCheckDto;
import io.nottodo.dto.NotTodoListDto;
import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.NotTodoListCheck;
import io.nottodo.entity.QNotTodoList;
import io.nottodo.entity.QNotTodoListCheck;
import io.nottodo.repository.NotTodoListCheckRepository;
import io.nottodo.repository.NotTodoListRepository;
import io.nottodo.request.NotTodoCheckRequest;
import io.nottodo.service.NotTodoListCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotTodoListCheckServiceImpl implements NotTodoListCheckService{
    
    private final NotTodoListRepository notTodoListRepository;
    private final NotTodoListCheckRepository notTodoListCheckRepository;
    private final JPAQueryFactory queryFactory;
    
    
    @Override
    public NotTodoListCheckDto notTodoCheck(NotTodoCheckRequest notTodoCheckRequest) {
        NotTodoList notTodoList = notTodoListRepository.findById(notTodoCheckRequest.getNotTodoListId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Not Todo"));
        NotTodoListCheck notTodoCheck = NotTodoListCheck.createNotTodoCheck(notTodoCheckRequest);
        notTodoCheck.setNotTodoList(notTodoList);
        NotTodoListCheck saveNotTodo = notTodoListCheckRepository.save(notTodoCheck);
        return NotTodoListCheckDto.createNotTodoListCheckDto(saveNotTodo);
    }
    
    @Override
    public NotTodoListCheckDto updateNotTodoCheck(Long checkId, NotTodoCheckRequest request) {
        NotTodoListCheck notTodoListCheck = notTodoListCheckRepository.findById(checkId).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 Not Todo Check"));
        notTodoListCheck.modifyCheckList(request);
        return NotTodoListCheckDto.createNotTodoListCheckDto(notTodoListCheck);
    }
  
    @Override
    public NotTodoListDto deleteTodoChecksAndUpdateEndDate(Long notTodoListId, LocalDate deleteFromDate) {
        QNotTodoListCheck qNotTodoListCheck = QNotTodoListCheck.notTodoListCheck;
        //5월 10일부터 5월 24일까지 NotTodoList 라고 가정 20일날 삭제를 눌렀다고 가정하면 20일날 포함해서 24일까지의 데이터를 NotTodoListCheck테이블에서 찾아옴
        List<NotTodoListCheck> checkDelete = queryFactory
                .selectFrom(qNotTodoListCheck)
                .where(qNotTodoListCheck.notTodoList.id.eq(notTodoListId)
                        .and(qNotTodoListCheck.checkDate.after(deleteFromDate)))
                .fetch();
        //삭제
        notTodoListCheckRepository.deleteAll(checkDelete);
        
        NotTodoList notTodoList = notTodoListRepository.findById(notTodoListId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Not Todo List ID: " + notTodoListId));
        
        // 20일 이라면 19일이라고 업데이트 해줘야해서 -1
        LocalDate updateDate = deleteFromDate.minusDays(1);
        
        notTodoList.modifyEndDate(updateDate);
        
        // 남아있는 NottodoCheck를 조회
        List<NotTodoListCheck> checkList = queryFactory
                .select(qNotTodoListCheck)
                .from(qNotTodoListCheck)
                .where(qNotTodoListCheck.notTodoList.id.eq(notTodoListId))
                .fetch();
        
        List<NotTodoListCheckDto> checkCollect = checkList.stream()
                .map(check -> {
                    NotTodoListCheckDto dto = new NotTodoListCheckDto();
                    dto.setCheckId(check.getCheckId());
                    dto.setCheckDate(check.getCheckDate());
                    dto.setCompliant(check.isCompliant());
                    dto.setCreateAt(check.getCreateAt());
                    dto.setUpdateAt(check.getUpdateAt());
                    dto.setNotTodoListId(check.getNotTodoList().getId());
                    return dto;
                }).toList();
        NotTodoListDto notTodoListDto = new NotTodoListDto();
        notTodoListDto.setNotTodoListId(notTodoList.getId());
        notTodoListDto.setMemberId(notTodoList.getMember().getId());
        notTodoListDto.setUsername(notTodoList.getMember().getUsername());
        notTodoListDto.setMemberName(notTodoList.getMember().getMemberName());
        notTodoListDto.setCategoryId(notTodoList.getCategory().getId());
        notTodoListDto.setCategoryName(notTodoList.getCategory().getCategoryName());
        notTodoListDto.setNotTodoListContent(notTodoList.getNotTodoListContent());
        notTodoListDto.setStartDate(notTodoList.getStartDate());
        notTodoListDto.setEndDate(notTodoList.getEndDate());
        notTodoListDto.setNotTodoListCheck(checkCollect);
        return notTodoListDto;
    }
}