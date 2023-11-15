package com.project.web.controller.community.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardPreviewDto {
    private final String type;
    private final String alias;
    private final String category;

    @Builder
    public BoardPreviewDto(String type, String alias, String category){
        this.type = type;
        this.alias = alias;
        this.category = category;
    }
}
