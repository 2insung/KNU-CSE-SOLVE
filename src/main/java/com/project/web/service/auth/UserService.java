package com.project.web.service.auth;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.auth.dto.UserDto;
import com.project.web.domain.member.Role;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MemberRepository memberRepository;

    /*
      유저 정보 출력 함수.
     * 현재 로그인한 유저의 정보를 출력하는 함수.
    */
    public UserDto getUserDto(PrincipalDetails principal) {
        if (principal != null) {
            Object result = memberRepository.findUserDtoByMemberId(principal.getUserId())
                    .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

            Object[] arr = (Object[]) result;
            Role role = (Role) arr[0];
            String nickname = (String) arr[1];
            String profileImage = (String) arr[2];

            return UserDto.builder()
                    .isLogin(true)
                    .userId(principal.getUserId())
                    .nickname(nickname)
                    .profileImage(profileImage)
                    .isAdmin(role.name().equals("ROLE_ADMIN"))
                    .build();
        }
        else {
            return UserDto.builder()
                    .isLogin(false)
                    .userId(null)
                    .nickname(null)
                    .profileImage(null)
                    .isAdmin(false)
                    .build();
        }
    }

}
