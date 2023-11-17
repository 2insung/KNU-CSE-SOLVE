package com.project.web.controller.community.dto.board.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SaveBoardResponseDto {
    private final String boardType;

    @Builder
    public SaveBoardResponseDto(String boardType){
        this.boardType = boardType;
    }
}
