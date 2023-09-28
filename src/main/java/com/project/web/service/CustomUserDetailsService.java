package com.project.web.service;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.domain.Member;
import com.project.web.domain.MemberAuth;
import com.project.web.domain.MemberProfile;
import com.project.web.repository.MemberAuthRepository;
import com.project.web.repository.MemberProfileRepository;
import com.project.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Object[] result = memberRepository.findByUsernameWithAuthAndProfile(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

        return createUserDetails(result);

    }

    private UserDetails createUserDetails(Object[] result) {
        Member member = (Member) result[0];
        MemberAuth memberAuth = (MemberAuth) result[1];
        MemberProfile memberProfile = (MemberProfile) result[2];

        return PrincipalDetails.builder()
                .member(member)
                .memberAuth(memberAuth)
                .memberProfile(memberProfile)
                .build();
    }
}
