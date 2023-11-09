package com.project.web.controller.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckCodeResponseDto {
    private final Boolean result;

    @Builder
    public CheckCodeResponseDto(Boolean result){
        this.result = result;
    }
}
