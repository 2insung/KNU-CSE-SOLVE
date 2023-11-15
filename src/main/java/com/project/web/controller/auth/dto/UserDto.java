package com.project.web.controller.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {
    private Boolean isLogin;
    private Integer userId;
    private String nickname;
    private String profileImage;
    private Boolean isAdmin;

    @Builder
    public UserDto(Boolean isLogin, Integer userId, String nickname, String profileImage, Boolean isAdmin) {
        this.isLogin = isLogin;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.isAdmin = isAdmin;
    }
}
