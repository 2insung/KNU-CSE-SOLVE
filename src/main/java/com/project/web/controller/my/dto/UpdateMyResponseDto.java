package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMyResponseDto {
    private String nickname;

    @Builder
    public UpdateMyResponseDto(String nickname){
        this.nickname = nickname;
    }
}
