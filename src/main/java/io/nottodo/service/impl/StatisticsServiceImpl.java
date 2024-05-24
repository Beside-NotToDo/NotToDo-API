package io.nottodo.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.nottodo.dto.*;
import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.NotTodoListCheck;
import io.nottodo.repository.CategoryRepository;
import io.nottodo.repository.NotTodoListCheckRepository;
import io.nottodo.repository.NotTodoListRepository;
import io.nottodo.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class StatisticsServiceImpl implements StatisticsService {
    
    private final NotTodoListRepository notTodoListRepository;
    private final NotTodoListCheckRepository notTodoListCheckRepository;
    private final CategoryRepository categoryRepository;
    private final JPAQueryFactory queryFactory;
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    
    @Override
    public ThemeStatisticsDto getThemeStatistics(Long memberId, Long categoryId) {
        List<NotTodoList> notTodoLists = notTodoListRepository.findAllByMemberIdAndCategoryId(memberId, categoryId);
        List<Long> notTodoListIds = notTodoLists.stream().map(NotTodoList::getId).collect(Collectors.toList());
        List<NotTodoListCheck> checks = notTodoListCheckRepository.findAllByNotTodoListIdIn(notTodoListIds);
        
        double averageScore = calculateAverageScore(notTodoLists, checks);
        LocalDate startDate = notTodoLists.stream().map(NotTodoList::getStartDate).min(LocalDate::compareTo).orElse(null);
        LocalDate endDate = notTodoLists.stream().map(NotTodoList::getEndDate).max(LocalDate::compareTo).orElse(null);
        
        List<NotTodoListContentDto> contents = new ArrayList<>();
        List<String> excludedContents = new ArrayList<>();
        List<String> reasons = new ArrayList<>();
        
        LocalDate currentDate = LocalDate.now();
        
        for (NotTodoList notTodoList : notTodoLists) {
            LocalDate validStartDate = notTodoList.getStartDate().plusDays(21);
            if (validStartDate.isAfter(currentDate)) {
                excludedContents.add(notTodoList.getNotTodoListContent());
                reasons.add("시작일로부터 21일이 지나지 않음");
                continue;
            }
            
            List<NotTodoListCheckDto> checkDtos = createCheckDtosForNotTodoList(notTodoList, checks, validStartDate, currentDate);
            contents.add(new NotTodoListContentDto(
                    notTodoList.getId(),
                    notTodoList.getNotTodoListContent(),
                    notTodoList.getStartDate(),
                    notTodoList.getEndDate(),
                    checkDtos
            ));
        }
        
        ThemeStatisticsDto dto = new ThemeStatisticsDto();
        dto.setCategoryId(categoryId);
        dto.setAverageScore((int) Math.floor(averageScore)); // 소수점 없이 정수형으로 변환
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setContents(contents);
        dto.setExcludeFromCounting(excludedContents);
        dto.setReason(reasons);
        
        return dto;
    }
    
    private List<NotTodoListCheckDto> createCheckDtosForNotTodoList(NotTodoList notTodoList, List<NotTodoListCheck> checks, LocalDate validStartDate, LocalDate currentDate) {
        List<NotTodoListCheckDto> checkDtos = new ArrayList<>();
        
        LocalDate startDate = notTodoList.getStartDate();
        LocalDate endDate = notTodoList.getEndDate().isAfter(currentDate) ? currentDate : notTodoList.getEndDate();
        
        for (LocalDate date = validStartDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDate finalDate = date;  // effectively final 변수로 변환
            
            Optional<NotTodoListCheck> optionalCheck = checks.stream()
                    .filter(check -> check.getNotTodoList().getId().equals(notTodoList.getId()) && check.getCheckDate().equals(finalDate))
                    .findFirst();
            
            if (optionalCheck.isPresent()) {
                checkDtos.add(NotTodoListCheckDto.createNotTodoListCheckDto(optionalCheck.get()));
            } else {
                // 체크 기록이 없는 날짜를 false로 처리
                checkDtos.add(new NotTodoListCheckDto(null, notTodoList.getId(), finalDate, notTodoList.getNotTodoListContent(), false, null, null));
            }
        }
        
        return checkDtos;
    }
    
    @Override
    public OverallStatisticsDto getOverallStatistics(Long memberId) {
        List<NotTodoList> notTodoLists = notTodoListRepository.findAllByMemberId(memberId);
        List<Long> notTodoListIds = notTodoLists.stream().map(NotTodoList::getId).collect(Collectors.toList());
        List<NotTodoListCheck> checks = notTodoListCheckRepository.findAllByNotTodoListIdInAndIsCompliantTrue(notTodoListIds);
        
        double averageScore = calculateAverageScore(notTodoLists, checks);
        LocalDate startDate = notTodoLists.stream().map(NotTodoList::getStartDate).min(LocalDate::compareTo).orElse(null);
        LocalDate endDate = notTodoLists.stream().map(NotTodoList::getEndDate).max(LocalDate::compareTo).orElse(null);
        
        OverallStatisticsDto dto = new OverallStatisticsDto();
        dto.setAverageScore(averageScore);
        dto.setChecks(checks.stream().map(NotTodoListCheckDto::createNotTodoListCheckDto).collect(Collectors.toList()));
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        return dto;
    }
    
    @Override
    public List<NotTodoListWithChecksDto> getNotTodoListsByCategoryWithChecks(Long memberId, Long categoryId) {
        List<NotTodoList> notTodoLists = notTodoListRepository.findAllByMemberIdAndCategoryId(memberId, categoryId);
        List<Long> notTodoListIds = notTodoLists.stream().map(NotTodoList::getId).collect(Collectors.toList());
        List<NotTodoListCheck> notTodoListChecks = notTodoListCheckRepository.findAllByNotTodoListIdIn(notTodoListIds);
        
        return notTodoLists.stream()
                .map(notTodoList -> {
                    List<NotTodoListCheckDto> checkDtos = createCheckDtosForNotTodoList(notTodoList, notTodoListChecks, notTodoList.getStartDate().plusDays(21), LocalDate.now());
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
    }
    
    private double calculateAverageScore(List<NotTodoList> notTodoLists, List<NotTodoListCheck> checks) {
        if (notTodoLists.isEmpty()) {
            return 0;
        }
        
        double totalScore = 0;
        int count = 0;
        LocalDate currentDate = LocalDate.now();
        
        for (NotTodoList list : notTodoLists) {
            LocalDate validStartDate = list.getStartDate().plusDays(21);
            
            if (validStartDate.isAfter(currentDate)) {
                continue; // 시작일로부터 21일이 지나지 않은 경우 무시
            }
            
            long totalDays = ChronoUnit.DAYS.between(validStartDate, currentDate) + 1;
            long compliantDays = checks.stream()
                    .filter(check -> check.getNotTodoList().getId().equals(list.getId()) && check.isCompliant())
                    .count();
            
            totalScore += (double) compliantDays / totalDays * 100;
            count++;
        }
        
        return count == 0 ? 0 : totalScore / count; // 평균 점수를 100으로 변환
    }
}