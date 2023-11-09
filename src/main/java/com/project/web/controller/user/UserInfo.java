package com.project.web.controller.user;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.user.dto.UserDto;

public class UserInfo {
    private UserInfo() {
    }

    public static UserDto getUserInfo(PrincipalDetails principal) {
        if (principal != null) {
            return UserDto.builder()
                    .isLogin(true)
                    .nickname(principal.getNickname())
                    .profileImage(principal.getProfileImage())
                    .build();
        }
        else {
            return UserDto.builder()
                    .isLogin(false)
                    .nickname(null)
                    .profileImage(null)
                    .build();
        }
    }
}
