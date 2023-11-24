package com.insung.knucsesolve.controller.community.dto.board.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SaveBoardResponseDto {
    private final Boolean isSuccess;

    @Builder
    public SaveBoardResponseDto(Boolean isSuccess){
        this.isSuccess = isSuccess;
    }
}
