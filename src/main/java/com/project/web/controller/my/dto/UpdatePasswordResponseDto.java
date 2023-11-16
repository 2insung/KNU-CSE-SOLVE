package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePasswordResponseDto {
    private Integer userId;

    @Builder
    public UpdatePasswordResponseDto(Integer userId){
        this.userId = userId;
    }
}
