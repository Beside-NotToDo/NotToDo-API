package io.nottodo.service.impl;

import io.nottodo.dto.MemberDto;
import io.nottodo.entity.Member;
import io.nottodo.entity.MemberRole;
import io.nottodo.entity.NotTodoList;
import io.nottodo.entity.Role;
import io.nottodo.repository.*;
import io.nottodo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final NotTodoListRepository notTodoListRepository;
    private final NotTodoListCheckRepository notTodoListCheckRepository;
    
    
    @Override
    public Member createMember(Member member) {
        Member saveMember = memberRepository.save(member);
        Role role = roleRepository.findByName("ROLE_USER");
        MemberRole memberRole = new MemberRole();
        memberRole.setMember(saveMember);
        memberRole.setRole(role);
        memberRoleRepository.save(memberRole);
        return member;
    }
    
    @Override
    public Member deleteMember(Long memberId) {
        // 삭제하기 전에 회원 정보 가져오기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + memberId));
        
        // 관련 데이터 삭제 순서
        // 1. 회원의 낫 투두 리스트 체크 삭제
        List<Long> notTodoListIds = notTodoListRepository.findAllByMemberId(memberId).stream()
                .map(NotTodoList::getId)
                .collect(Collectors.toList());
        if (!notTodoListIds.isEmpty()) {
            notTodoListCheckRepository.deleteAllByNotTodoListIdIn(notTodoListIds);
        }
        
        // 2. 회원의 낫 투두 리스트 삭제
        notTodoListRepository.deleteAllByMemberId(memberId);
        
        // 3. 회원의 역할 삭제
        memberRoleRepository.deleteAllByMemberId(memberId);
        
        // 4. 회원 삭제
        memberRepository.deleteById(memberId);
        
        // 삭제된 회원 정보 반환
        return member;
    }
}
