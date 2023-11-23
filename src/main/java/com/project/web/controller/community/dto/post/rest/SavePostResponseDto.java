package com.project.web.controller.community.dto.post.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SavePostResponseDto {
    private final Integer boardId;

    @Builder
    public SavePostResponseDto(Integer boardId){
        this.boardId = boardId;
    }
}
