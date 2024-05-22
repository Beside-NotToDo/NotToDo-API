package io.nottodo.service.impl;

import io.nottodo.dto.DailyComplianceDto;
import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.NotTodoListCheck;
import io.nottodo.repository.NotTodoListCheckRepository;
import io.nottodo.repository.NotTodoListRepository;
import io.nottodo.service.NotTodoListCheckService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotTodoListCheckServiceImpl implements NotTodoListCheckService{
    private final NotTodoListRepository notTodoListRepository;
    private final NotTodoListCheckRepository notTodoListCheckRepository;
    
    public List<DailyComplianceDto> getMonthlyCompliance(Long memberId, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        
        // 해당 회원의 특정 기간에 해당하는 모든 낫 투두 리스트 항목을 조회
        List<NotTodoList> notTodoLists = notTodoListRepository.findAllByMemberIdAndStartDateBeforeAndEndDateAfter(memberId, endDate, startDate);
        
        List<DailyComplianceDto> complianceList = new ArrayList<>();
        
        LocalDate currentDate = startDate;
        // 월의 모든 날짜를 순회
        while (!currentDate.isAfter(endDate)) {
            int totalChecks = 0;
            int compliantChecks = 0;
            
            // 모든 낫 투두 리스트 항목에 대해 현재 날짜에 대한 준수 여부를 확인
            for (NotTodoList notTodoList : notTodoLists) {
                // 현재 날짜가 낫 투두 리스트 항목의 기간에 포함되는지 확인
                if (!notTodoList.getStartDate().isAfter(currentDate) && !notTodoList.getEndDate().isBefore(currentDate)) {
                    Optional<NotTodoListCheck> check = notTodoListCheckRepository.findByNotTodoListIdAndCheckDate(notTodoList.getId(), currentDate);
                    boolean isCompliant = check.map(NotTodoListCheck::isCompliant).orElse(false);
                    
                    totalChecks++;
                    if (isCompliant) {
                        compliantChecks++;
                    }
                }
            }
            
            // 등록된 낫 투두 리스트 항목이 있는 경우에만 리스트에 추가
            if (totalChecks > 0) {
                int complianceRate = (int) Math.floor((double) compliantChecks / totalChecks * 100);
                complianceList.add(new DailyComplianceDto(currentDate, complianceRate));
            }
            
            currentDate = currentDate.plusDays(1);
        }
        
        return complianceList;
    }
}