package com.project.web.service.auth;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.domain.member.Authority;
import com.project.web.domain.member.Level;
import com.project.web.repository.member.MemberRepository;
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
        Object result = memberRepository.findByUsernameForLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

        return createUserDetails(result);
    }

    private UserDetails createUserDetails(Object result) {
        Object[] arr = (Object[]) result;
        Integer memberId = (Integer) arr[0];
        String username = (String) arr[1];
        String password = (String) arr[2];
        String nickname = (String) arr[3];
        String profileImage = (String) arr[4];
        Authority role = (Authority) arr[5];
        Level level = (Level) arr[6];

        return PrincipalDetails.builder()
                .userId(memberId)
                .username(username)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .role(role)
                .level(level)
                .build();
    }
}
