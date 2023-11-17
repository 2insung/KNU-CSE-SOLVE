package com.project.web.controller.community.dto.post.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SavePostResponseDto {
    private final String boardType;

    @Builder
    public SavePostResponseDto(String boardType){
        this.boardType = boardType;
    }
}
