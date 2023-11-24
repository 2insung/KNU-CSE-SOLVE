package com.insung.knucsesolve.controller.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {
    private final Integer userId;
    private final String username;
    private final String nickname;
    private final String profileImage;

    @Builder
    public UserDto(Integer userId, String username, String nickname, String profileImage) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
