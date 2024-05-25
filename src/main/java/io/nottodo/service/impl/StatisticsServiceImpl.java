package io.nottodo.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.nottodo.dto.*;
import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.NotTodoListCheck;
import io.nottodo.entity.QNotTodoList;
import io.nottodo.entity.QNotTodoListCheck;
import io.nottodo.repository.NotTodoListCheckRepository;
import io.nottodo.repository.NotTodoListRepository;
import io.nottodo.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class StatisticsServiceImpl implements StatisticsService {
    private final NotTodoListRepository notTodoListRepository;
    private final NotTodoListCheckRepository notTodoListCheckRepository;
    private final JPAQueryFactory queryFactory;
    
    @Override
    public ThemeStatisticsDto getThemeStatistics(Long memberId, Long categoryId) {
        List<NotTodoList> notTodoLists = notTodoListRepository.findAllByMemberIdAndCategoryId(memberId, categoryId);
        List<Long> notTodoListIds = notTodoLists.stream().map(NotTodoList::getId).collect(Collectors.toList());
        List<NotTodoListCheck> checks = notTodoListCheckRepository.findAllByNotTodoListIdIn(notTodoListIds);
        
        LocalDate currentDate = LocalDate.now();
        LocalDate earliestStartDate = currentDate.minusDays(21);
        
        List<NotTodoList> filteredNotTodoLists = new ArrayList<>();
        List<String> excludeFromCounting = new ArrayList<>();
        List<String> reason = new ArrayList<>();
        
        for (NotTodoList list : notTodoLists) {
            if (!list.getStartDate().isAfter(earliestStartDate)) {
                filteredNotTodoLists.add(list);
            } else {
                excludeFromCounting.add(list.getNotTodoListContent());
                reason.add("시작일로부터 21일이 지나지 않음");
            }
        }
        
        double averageScore = calculateAverageScore(filteredNotTodoLists);
        LocalDate startDate = filteredNotTodoLists.stream().map(NotTodoList::getStartDate).min(LocalDate::compareTo).orElse(null);
        LocalDate endDate = filteredNotTodoLists.stream().map(NotTodoList::getEndDate).max(LocalDate::compareTo).orElse(null);
        
        List<NotTodoListWithChecksDto> contents = filteredNotTodoLists.stream()
                .map(notTodoList -> {
                    List<NotTodoListCheckDto> checkDtos = new ArrayList<>();
                    LocalDate date = notTodoList.getStartDate();
                    while (!date.isAfter(notTodoList.getEndDate())) {
                        final LocalDate currentDateFinal = date;
                        Optional<NotTodoListCheck> check = checks.stream()
                                .filter(c -> c.getNotTodoList().getId().equals(notTodoList.getId()) && c.getCheckDate().equals(currentDateFinal))
                                .findFirst();
                        if (check.isPresent()) {
                            checkDtos.add(NotTodoListCheckDto.createNotTodoListCheckDto(check.get()));
                        } else {
                            checkDtos.add(new NotTodoListCheckDto(null, notTodoList.getId(), currentDateFinal, notTodoList.getNotTodoListContent(), false, null, null));
                        }
                        date = date.plusDays(1);
                    }
                    return new NotTodoListWithChecksDto(
                            notTodoList.getId(),
                            notTodoList.getNotTodoListContent(),
                            notTodoList.getStartDate(),
                            notTodoList.getEndDate(),
                            notTodoList.getMember().getId(),
                            notTodoList.getMember().getUsername(),
                            notTodoList.getMember().getMemberName(),
                            notTodoList.getCategory().getId(),
                            notTodoList.getCategory().getCategoryName(),
                            checkDtos
                    );
                })
                .collect(Collectors.toList());
        
        ThemeStatisticsDto dto = new ThemeStatisticsDto();
        dto.setCategoryId(categoryId);
        dto.setAverageScore(Math.floor(averageScore * 100) / 100); // 소수점 2자리로
        dto.setExcludeFromCounting(excludeFromCounting);
        dto.setReason(reason);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setContents(contents);
        return dto;
    }
    

    
    @Override
    public List<CategoryAverageScoreDto> getCategoryAverageScores(Long memberId) {
        List<NotTodoList> notTodoLists = notTodoListRepository.findAllByMemberId(memberId);
        LocalDate currentDate = LocalDate.now();
        
        Map<Long, List<NotTodoList>> categoryNotTodoListsMap = notTodoLists.stream()
                .collect(Collectors.groupingBy(notTodoList -> notTodoList.getCategory().getId()));
        
        List<CategoryAverageScoreDto> result = new ArrayList<>();
        
        for (Map.Entry<Long, List<NotTodoList>> entry : categoryNotTodoListsMap.entrySet()) {
            Long categoryId = entry.getKey();
            List<NotTodoList> lists = entry.getValue();
            String categoryName = lists.get(0).getCategory().getCategoryName();
            
            double totalScore = 0;
            int count = 0;
            
            for (NotTodoList list : lists) {
                LocalDate validStartDate = list.getStartDate().plusDays(21);
                if (validStartDate.isAfter(currentDate)) {
                    continue;
                }
                
                long totalDays = ChronoUnit.DAYS.between(validStartDate, list.getEndDate()) + 1;
                long compliantDays = notTodoListCheckRepository.countByNotTodoListIdAndIsCompliantTrue(list.getId());
                totalScore += (double) compliantDays / totalDays;
                count++;
            }
            
            double averageScore = count == 0 ? 0 : totalScore / count;
            result.add(new CategoryAverageScoreDto(categoryId, categoryName, Math.floor(averageScore * 100) / 100));
        }
        
        return result;
    }
    
    
    private double calculateAverageScore(List<NotTodoList> notTodoLists) {
        if (notTodoLists.isEmpty()) {
            return 0;
        }
        
        double totalScore = 0;
        int count = 0;
        
        for (NotTodoList list : notTodoLists) {
            long totalDays = ChronoUnit.DAYS.between(list.getStartDate().plusDays(21), list.getEndDate()) + 1;
            long compliantDays = notTodoListCheckRepository.countByNotTodoListIdAndIsCompliantTrue(list.getId());
            totalScore += (double) compliantDays / totalDays;
            count++;
        }
        
        return count == 0 ? 0 : totalScore / count;
    }
}