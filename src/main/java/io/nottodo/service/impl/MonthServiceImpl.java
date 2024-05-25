package io.nottodo.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.nottodo.dto.day.DailyDto;
import io.nottodo.dto.day.HeaderDto;
import io.nottodo.dto.day.MonthDto;
import io.nottodo.dto.day.WeekDto;
import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.NotTodoListCheck;
import io.nottodo.entity.QNotTodoList;
import io.nottodo.entity.QNotTodoListCheck;
import io.nottodo.service.MonthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
@Transactional
@RequiredArgsConstructor
@Service
public class MonthServiceImpl implements MonthService {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public MonthDto getMonthAndDaily(Long memberId, YearMonth yearMonth) {
        MonthDto monthDto = new MonthDto();
        monthDto.setHeader(getMonthlyHeader(memberId, yearMonth));
        List<DailyDto> dailyForMonth = getDailyForMonth(memberId, yearMonth);
        monthDto.setDaily(dailyForMonth);
        return monthDto;
    }
    
    @Override
    public WeekDto getWeekAndDaily(Long memberId, Integer month, Integer week) {
        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
        LocalDate startDate = getStartOfWeek(yearMonth, week);
        LocalDate endDate = getEndOfWeek(startDate);
        WeekDto weekDto = new WeekDto();
        weekDto.setStartDate(startDate.toString());
        weekDto.setEndDate(endDate.toString());
        weekDto.setHeader(getWeeklyHeader(memberId, startDate, endDate));
        weekDto.setDaily(getDailyForWeek(memberId, startDate, endDate));
        return weekDto;
    }
    
    private LocalDate getStartOfWeek(YearMonth yearMonth, int week) {
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate firstSunday = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        return firstSunday.plusWeeks(week);
    }
    
    private LocalDate getEndOfWeek(LocalDate startOfWeek) {
        return startOfWeek.plusDays(6);
    }
    
    private HeaderDto getMonthlyHeader(Long memberId, YearMonth month) {
        HeaderDto headerDto = new HeaderDto();
        headerDto.setMonth(month.getYear() + "년 " + month.getMonthValue() + "월");
        
        List<HeaderDto.WeeklyDto> collect = month.atDay(1)
                .datesUntil(month.atEndOfMonth().plusDays(1))
                .map(day -> {
                    HeaderDto.WeeklyDto weeklyDto = new HeaderDto.WeeklyDto();
                    weeklyDto.setDate(day.toString());
                    weeklyDto.setWeekDay(day.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN));
                    if (day.isAfter(LocalDate.now())) {
                        weeklyDto.setComplianceType(null);
                    } else {
                        weeklyDto.setComplianceType(getComplianceType(memberId, day));
                    }
                    return weeklyDto;
                }).collect(Collectors.toList());
        headerDto.setWeekly(collect);
        return headerDto;
    }
    
    private HeaderDto getWeeklyHeader(Long memberId, LocalDate startDate, LocalDate endDate) {
        HeaderDto headerDto = new HeaderDto();
        headerDto.setMonth(startDate.getYear() + "년 " + startDate.getMonthValue() + "월");
        
        List<HeaderDto.WeeklyDto> weekly = startDate.datesUntil(endDate.plusDays(1))
                .map(day -> {
                    HeaderDto.WeeklyDto weeklyDto = new HeaderDto.WeeklyDto();
                    weeklyDto.setDate(day.toString());
                    weeklyDto.setWeekDay(day.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN));
                    if (day.isAfter(LocalDate.now())) {
                        weeklyDto.setComplianceType(null);
                    } else {
                        weeklyDto.setComplianceType(getComplianceType(memberId, day));
                    }
                    return weeklyDto;
                }).collect(Collectors.toList());
        headerDto.setWeekly(weekly);
        return headerDto;
    }
    
    private List<DailyDto> getDailyForWeek(Long memberId, LocalDate startDate, LocalDate endDate) {
        List<DailyDto> dailyList = new ArrayList<>();
        
        for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {
            dailyList.add(getDaily(memberId, currentDate));
        }
        
        return dailyList;
    }
    
    private List<DailyDto> getDailyForMonth(Long memberId, YearMonth yearMonth) {
        List<DailyDto> dailyList = new ArrayList<>();
        
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        
        for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {
            dailyList.add(getDaily(memberId, currentDate));
        }
        
        return dailyList;
    }
    
    private DailyDto getDaily(Long memberId, LocalDate date) {
        QNotTodoList qNotTodoList = QNotTodoList.notTodoList;
        QNotTodoListCheck qNotTodoListCheck = QNotTodoListCheck.notTodoListCheck;
        DailyDto dailyDto = new DailyDto();
        dailyDto.setDate(date.toString());
        
        List<NotTodoList> notTodoLists = queryFactory
                .selectFrom(qNotTodoList)
                .where(qNotTodoList.member.id.eq(memberId)
                        .and(qNotTodoList.startDate.loe(date))
                        .and(qNotTodoList.endDate.goe(date)))
                .fetch();
        
        List<Long> notTodoListIds = notTodoLists.stream()
                .map(NotTodoList::getId)
                .collect(Collectors.toList());
        
        List<NotTodoListCheck> notTodoListChecks = queryFactory
                .selectFrom(qNotTodoListCheck)
                .where(qNotTodoListCheck.notTodoList.id.in(notTodoListIds)
                        .and(qNotTodoListCheck.checkDate.eq(date)))
                .fetch();
        
        List<DailyDto.TodoDto> todos = notTodoLists
                .stream()
                .map(notTodoList -> {
                    DailyDto.TodoDto todoDto = new DailyDto.TodoDto();
                    todoDto.setNotTodoListContent(notTodoList.getNotTodoListContent());
                    todoDto.setCategoryId(notTodoList.getCategory().getId());
                    boolean isCheck = notTodoListChecks.stream()
                            .anyMatch(check -> check.getNotTodoList().getId().equals(notTodoList.getId())
                                    && check.isCompliant()
                            );
                    todoDto.setChecked(isCheck);
                    todoDto.setPeriod(ChronoUnit.DAYS.between(notTodoList.getStartDate(), date) + 1);
                    return todoDto;
                }).collect(Collectors.toList());
        
        dailyDto.setTodos(todos);
        return dailyDto;
    }
    
    private String getComplianceType(Long memberId, LocalDate date) {
        QNotTodoList qNotTodoList = QNotTodoList.notTodoList;
        QNotTodoListCheck qNotTodoListCheck = QNotTodoListCheck.notTodoListCheck;
        
        if (date.isAfter(LocalDate.now())) {
            return null;
        }
        
        Long totalCount = queryFactory
                .select(qNotTodoList.count())
                .from(qNotTodoList)
                .where(qNotTodoList.member.id.eq(memberId)
                        .and(qNotTodoList.startDate.loe(date))
                        .and(qNotTodoList.endDate.goe(date)))
                .fetchOne();
        
        Long successCount = queryFactory
                .select(qNotTodoListCheck.count())
                .from(qNotTodoListCheck)
                .join(qNotTodoList).on(qNotTodoListCheck.notTodoList.id.eq(qNotTodoList.id))
                .where(qNotTodoList.member.id.eq(memberId)
                        .and(qNotTodoListCheck.checkDate.eq(date))
                        .and(qNotTodoListCheck.isCompliant.isTrue()))
                .fetchOne();
        
        int rate = (totalCount == 0) ? 0 : (int) Math.floor((double) successCount / totalCount * 100);
        
        if (totalCount == 0) {
            return null;
        } else if (rate == 100) {
            return "good";
        } else if (rate >= 50) {
            return "fair";
        } else {
            return "poor";
        }
    }
}