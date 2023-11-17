package com.project.web.controller.community.dto.post.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeletePostResponseDto {
    private final String boardType;

    @Builder
    public DeletePostResponseDto(String boardType){
        this.boardType = boardType;
    }
}
