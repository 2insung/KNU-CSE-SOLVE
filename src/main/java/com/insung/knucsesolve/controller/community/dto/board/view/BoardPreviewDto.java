package com.insung.knucsesolve.controller.community.dto.board.view;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardPreviewDto {
    // Entity : Board
    private final Integer id;
    private final String alias;
    private final String category;

    @Builder
    public BoardPreviewDto(Integer id, String alias, String category){
        this.id = id;
        this.alias = alias;
        this.category = category;
    }
}
