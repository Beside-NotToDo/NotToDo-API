package io.nottodo.service;

import io.nottodo.dto.MemberDto;
import io.nottodo.entity.Member;

public interface MemberService {
    
    
    Member createMember(Member member);
    
    MemberDto deleteMember(Long memberId);
}
