package com.insung.knucsesolve.controller.community.dto.post.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeletePostResponseDto {
    private final Integer boardId;

    @Builder
    public DeletePostResponseDto(Integer boardId){
        this.boardId = boardId;
    }
}
