package com.insung.knucsesolve.controller.auth.dto.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpResponseDto {
    private final Boolean isSuccess;

    @Builder
    public SignUpResponseDto(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
