package io.nottodo.security.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.nottodo.context.MemberContext;
import io.nottodo.dto.MemberDto;
import io.nottodo.entity.Member;
import io.nottodo.login.LoginType;
import io.nottodo.repository.MemberRepository;
import io.nottodo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Member member = memberRepository.findByUsername(username);
        
        if (member == null) {
            Member createMember = new Member(username, UUID.randomUUID().toString(), UUID.randomUUID().toString(), LoginType.KAKAO);
            member = memberService.createMember(createMember);
        }
        
        MemberDto memberDto = MemberDto.createMemberDto(member);
        
        List<GrantedAuthority> authorities = memberDto.getMemberRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        
        return new MemberContext(memberDto,authorities);
    }
    
    @Transactional
    public UserDetails loadUserByUsernameAndKakaoInfo(String username, JsonNode kakaoUserInfo) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        
        if (member == null) {
            
            String realName = kakaoUserInfo.get("properties").get("nickname").asText(); // 실명
            Member createMember = new Member(username, UUID.randomUUID().toString(), realName, LoginType.KAKAO);
            member = memberService.createMember(createMember);
        }
        
        MemberDto memberDto = MemberDto.createMemberDto(member);
        List<GrantedAuthority> authorities = memberDto.getMemberRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        
        return new MemberContext(memberDto, authorities);
    }
}
