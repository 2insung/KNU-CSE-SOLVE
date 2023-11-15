package com.project.web.service.auth;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.auth.dto.UserDto;
import com.project.web.domain.member.Authority;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MemberRepository memberRepository;

    public UserDto getUserDto(PrincipalDetails principal) {
        if (principal != null) {
            Object result = memberRepository.findUserByMemberId(principal.getUserId())
                    .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

            Object[] arr = (Object[]) result;
            String nickname = (String) arr[0];
            String profileImage = (String) arr[1];
            Authority role = (Authority) arr[2];

            return UserDto.builder()
                    .isLogin(true)
                    .nickname(nickname)
                    .profileImage(profileImage)
                    .isAdmin(role.name().equals("ROLE_ADMIN"))
                    .build();
        }
        else {
            return UserDto.builder()
                    .isLogin(false)
                    .nickname(null)
                    .profileImage(null)
                    .isAdmin(false)
                    .build();
        }
    }

}
