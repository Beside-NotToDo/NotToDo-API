package io.nottodo.service.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.nottodo.dto.NotTodoListDto;
import io.nottodo.entity.*;
import io.nottodo.repository.CategoryRepository;
import io.nottodo.repository.MemberRepository;
import io.nottodo.repository.NotTodoListRepository;
import io.nottodo.request.NotTodoListRequest;
import io.nottodo.request.NotTodoListTemporaryStorageRequest;
import io.nottodo.service.NotTodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.Projections.*;


@RequiredArgsConstructor
@Service
@Transactional
public class NotTodoListServiceImpl implements NotTodoListService {
    
    private final NotTodoListRepository notTodoListRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final JPAQueryFactory queryFactory;
    
    @Override
    @Transactional(readOnly = true)
    public List<NotTodoListDto> getNotTodoList(Long memberId) {
        QMember member = QMember.member;
        QNotTodoList notTodoList = QNotTodoList.notTodoList;
        QCategory category = QCategory.category;
        return queryFactory
                .select(notTodoList)
                .from(notTodoList)
                .leftJoin(notTodoList.member, member).fetchJoin()
                .leftJoin(notTodoList.category, category).fetchJoin()
                .where(member.id.eq(memberId)
                        .and(notTodoList.temporaryStorage.isFalse())
                )
                .fetch()
                .stream()
                .map(n -> new NotTodoListDto(
                        n.getId(),
                        n.getNotTodoListContent(),
                        n.getStartDate(),
                        n.getEndDate(),
                        n.getMember().getId(),
                        n.getMember().getUsername(),
                        n.getMember().getMemberName(),
                        n.getCategory().getId(),
                        n.getCategory().getCategoryName()
                ))
                .collect(Collectors.toList());
}
   
    @Override
    @Transactional(readOnly = true)
    public NotTodoListDto getNotTodoList(Long id, Long memberId) {
        QMember member = QMember.member;
        QNotTodoList notTodoList = QNotTodoList.notTodoList;
        QCategory category = QCategory.category;
        NotTodoList notTodo = queryFactory
                .select(notTodoList)
                .from(notTodoList)
                .leftJoin(notTodoList.member, member).fetchJoin()
                .leftJoin(notTodoList.category, category).fetchJoin()
                .where(notTodoList.id.eq(id), notTodoList.member.id.eq(memberId))
                .fetchOne();
        
        return new NotTodoListDto(
                notTodo.getId()
                , notTodo.getNotTodoListContent()
                , notTodo.getStartDate()
                , notTodo.getEndDate()
                , notTodo.getMember().getId()
                , notTodo.getMember().getUsername()
                , notTodo.getMember().getMemberName()
                , notTodo.getCategory().getId()
                , notTodo.getCategory().getCategoryName()
        );
        
        
    }
    
    @Override
    public List<NotTodoListDto> getAllNotTodoTemporaryStorage(Long memberId) {
        QMember member = QMember.member;
        QNotTodoList notTodoList = QNotTodoList.notTodoList;
        QCategory category = QCategory.category;
        return queryFactory
                .select(notTodoList)
                .from(notTodoList)
                .leftJoin(notTodoList.member, member).fetchJoin()
                .leftJoin(notTodoList.category, category).fetchJoin()
                .where(member.id.eq(memberId)
                        .and(notTodoList.temporaryStorage.isTrue())
                )
                .fetch()
                .stream()
                .map(n -> new NotTodoListDto(
                        n.getId(),
                        n.getNotTodoListContent(),
                        n.getStartDate(),
                        n.getEndDate(),
                        n.getMember().getId(),
                        n.getMember().getUsername(),
                        n.getMember().getMemberName(),
                        n.getCategory().getId(),
                        n.getCategory().getCategoryName()
                ))
                .collect(Collectors.toList());
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
    public Long modifyTemporaryStorageNotTodoList(Long id, NotTodoListTemporaryStorageRequest notTodoListTemporaryStorageRequest, Long memberId) {
        NotTodoList notTodoList = notTodoListRepository.findByIdAndMemberId(id, memberId);
        
        if (notTodoList == null) {
            throw new IllegalArgumentException("Not Found Todo");
        }
        
        notTodoList.updateNotTodoTemporaryStorage(notTodoListTemporaryStorageRequest);
        
        Category findCategory = categoryRepository.findById(notTodoListTemporaryStorageRequest.getCategoryId())
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
