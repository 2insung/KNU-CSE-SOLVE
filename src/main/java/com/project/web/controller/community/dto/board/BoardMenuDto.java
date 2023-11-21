package com.project.web.controller.community.dto.board;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardMenuDto {
    private final Integer number;
    private final List<BoardPreviewDto> boardPreviewList;

    @Builder
    public BoardMenuDto(Integer number, List<BoardPreviewDto> boardPreviewList) {
        this.number = number;
        this.boardPreviewList = boardPreviewList;
    }
}
