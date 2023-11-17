package com.project.web.controller.community.dto.post.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePostResponseDto {
    private final String boardType;
    private final String postId;

    @Builder
    public UpdatePostResponseDto(String boardType, Integer postId){
        this.boardType = boardType;
        this.postId = postId.toString();
    }
}
