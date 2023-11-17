package com.project.web.controller.my.dto.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePasswordResponseDto {
    private final Integer memberId;

    @Builder
    public UpdatePasswordResponseDto(Integer memberId){
        this.memberId = memberId;
    }
}
