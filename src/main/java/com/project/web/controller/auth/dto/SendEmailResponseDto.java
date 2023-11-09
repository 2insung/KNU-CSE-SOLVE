package com.project.web.controller.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SendEmailResponseDto {
    private final Boolean isExistingUsername;
    private final Boolean isExistingNickname;
    private final String code;

    @Builder
    public SendEmailResponseDto(Boolean isExistingUsername, Boolean isExistingNickname, String code) {
        this.isExistingUsername = isExistingUsername;
        this.isExistingNickname = isExistingNickname;
        this.code = code;
    }
}
