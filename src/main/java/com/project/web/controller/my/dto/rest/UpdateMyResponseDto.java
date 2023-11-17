package com.project.web.controller.my.dto.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMyResponseDto {
    private final Integer memberId;

    @Builder
    public UpdateMyResponseDto(Integer memberId) {
        this.memberId = memberId;
    }
}
