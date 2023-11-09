package com.project.web.controller.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpResponseDto {
    private final Boolean result;

    @Builder
    public SignUpResponseDto(Boolean result) {
        this.result = result;
    }
}
