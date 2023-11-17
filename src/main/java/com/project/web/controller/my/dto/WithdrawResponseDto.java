package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WithdrawResponseDto {
    private Boolean result;

    @Builder
    public WithdrawResponseDto(Boolean result){
        this.result = result;
    }
}
