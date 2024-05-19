package io.nottodo.service.impl;

import io.nottodo.dto.MemberDto;
import io.nottodo.entity.Member;
import io.nottodo.entity.MemberRole;
import io.nottodo.entity.Role;
import io.nottodo.repository.MemberRepository;
import io.nottodo.repository.MemberRoleRepository;
import io.nottodo.repository.RoleRepository;
import io.nottodo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;
    
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
}
