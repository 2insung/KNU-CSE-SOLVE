package com.project.web.controller.auth.dto;

import lombok.Builder;
import lombok.Getter;

// 사용자(세션) 정보
// 타임리프 템플릿의 변수로 사용.
// isLogin, nickname, profileImage의 경우 Header.html에 사용됨.
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
