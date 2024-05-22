package io.nottodo.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.nottodo.dto.NotTodoListDto;
import io.nottodo.entity.*;
import io.nottodo.repository.CategoryRepository;
import io.nottodo.repository.MemberRepository;
import io.nottodo.repository.NotTodoListRepository;
import io.nottodo.request.NotTodoListRequest;
import io.nottodo.service.NotTodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.querydsl.core.types.Projections.*;


@RequiredArgsConstructor
@Service
public class NotTodoListServiceImpl implements NotTodoListService {
    
    private final NotTodoListRepository notTodoListRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<NotTodoListDto> getNotTodoList(Long memberId) {
        QMember member = QMember.member;
        QNotTodoList notTodoList = QNotTodoList.notTodoList;
        QCategory category = QCategory.category;
        
        return queryFactory
                .select(bean(NotTodoListDto.class,
                        notTodoList.id.as("notTodoListId"),
                        notTodoList.notTodoListContent.as("notTodoListContent"),
                        notTodoList.startDate,
                        notTodoList.endDate,
                        member.id.as("memberId"),
                        member.username.as("username"),
                        member.memberName.as("memberName"),
                        category.id.as("categoryId"),
                        category.categoryName
                ))
                .from(notTodoList)
                .join(notTodoList.member, member).fetchJoin()
                .join(notTodoList.category, category).fetchJoin()
                .where(member.id.eq(memberId))
                .fetch();
    }
   
    @Override
    public NotTodoListDto getNotTodoList(Long id, Long memberId) {
        QNotTodoList notTodoList = QNotTodoList.notTodoList;
        return queryFactory
                .select(bean(NotTodoListDto.class,
                        notTodoList.id,
                        notTodoList.notTodoListContent,
                        notTodoList.startDate,
                        notTodoList.endDate
                ))
                .from(notTodoList)
                .where(notTodoList.id.eq(id), notTodoList.member.id.eq(memberId))
                .fetchOne();
    }
    
    
    @Override
    public Long createNotTodoList(NotTodoListRequest request, Long memberId) {
        NotTodoList notTodoList = NotTodoList.createNotTodoEntity(request.getNotTodoListContent(), request.getStartDate(), request.getEndDate());
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Not Found Member"));
        Category findCategory = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Not Found Category"));
        notTodoList.setMember(findMember);
        notTodoList.setCategory(findCategory);
        NotTodoList saveNotTodo = notTodoListRepository.save(notTodoList);
        return saveNotTodo.getId();
    }
    
    @Override
    public Long modifyNotTodoList(Long id,  NotTodoListRequest request, Long memberId) {
        NotTodoList notTodoList = notTodoListRepository.findByIdAndMemberId(id, memberId);
        
        if (notTodoList == null) {
            throw new IllegalArgumentException("Not Found Todo");
        }
        
        notTodoList.updateNotTodoEntity(request.getNotTodoListContent(),request.getStartDate(),request.getEndDate());
        
        Category findCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Not Found Category"));
        notTodoList.setCategory(findCategory);
        
        return notTodoList.getId();
    }
    
    @Override
    public Long deleteNotTodoList(Long id, Long memberId) {
        NotTodoList notTodoList = notTodoListRepository.findByIdAndMemberId(id, memberId);
        notTodoListRepository.delete(notTodoList);
        return id;
    }
}
