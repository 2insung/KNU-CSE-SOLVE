package com.project.web.controller.my.dto.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMyPasswordResponseDto {
    private final Integer memberId;

    @Builder
    public UpdateMyPasswordResponseDto(Integer memberId){
        this.memberId = memberId;
    }
}
