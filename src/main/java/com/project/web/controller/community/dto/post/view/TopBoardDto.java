package com.project.web.controller.community.dto.post.view;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TopBoardDto {
    // domain : board
    private final String alias;
    private final String type;
    private final List<TopPostDto> topPostDtos;

    @Builder
    public TopBoardDto(String alias, String type, List<TopPostDto> topPostDtos) {
        this.alias = alias;
        this.type = type;
        this.topPostDtos = topPostDtos;
    }
}
