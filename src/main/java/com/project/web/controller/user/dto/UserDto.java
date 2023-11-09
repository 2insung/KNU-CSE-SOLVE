package com.project.web.controller.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {
    private Boolean isLogin;
    private String nickname;
    private String profileImage;

    @Builder
    public UserDto(Boolean isLogin, String nickname, String profileImage) {
        this.isLogin = isLogin;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
