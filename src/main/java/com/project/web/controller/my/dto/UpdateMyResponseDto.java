package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMyResponseDto {
    private Integer userId;

    @Builder
    public UpdateMyResponseDto(Integer userId){
        this.userId = userId;
    }
}
