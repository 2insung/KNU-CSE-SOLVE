package com.insung.knucsesolve.service.auth;

import com.insung.knucsesolve.controller.auth.dto.PrincipalDetails;
import com.insung.knucsesolve.repository.member.MemberRepository;
import com.insung.knucsesolve.controller.auth.dto.UserDto;
import com.insung.knucsesolve.exception.Error500Exception;
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
                    .orElseThrow(() -> new Error500Exception("존재하지 않는 사용자입니다."));

            Object[] arr = (Object[]) result;
            Integer userId = (Integer) arr[0];
            String username = (String) arr[1];
            String nickname = (String) arr[2];
            String profileImage = (String) arr[3];

            return UserDto.builder()
                    .userId(userId)
                    .username(username)
                    .nickname(nickname)
                    .profileImage(profileImage)
                    .build();
        }
        else {
            return UserDto.builder()
                    .userId(null)
                    .username(null)
                    .nickname(null)
                    .profileImage(null)
                    .build();
        }
    }

}