package com.project.web.service.auth;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.domain.member.Role;
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
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Object result = memberRepository.findByUsernameForLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));

        return createUserDetails(result);
    }

    /*
      로그인 함수.
     * 입력한 username과 password에 해당하는 사용자가 존재하면, 사용자 정보가 담긴 principal을 반환함.
    */
    private UserDetails createUserDetails(Object result) {
        Object[] arr = (Object[]) result;
        Integer memberId = (Integer) arr[0];
        String username = (String) arr[1];
        String password = (String) arr[2];
        Role role = (Role) arr[3];

        return PrincipalDetails.builder()
                .userId(memberId)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
