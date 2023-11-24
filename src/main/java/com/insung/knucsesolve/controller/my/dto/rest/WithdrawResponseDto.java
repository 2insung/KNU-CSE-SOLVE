package com.insung.knucsesolve.controller.my.dto.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WithdrawResponseDto {
    private final Boolean isSuccess;

    @Builder
    public WithdrawResponseDto(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
