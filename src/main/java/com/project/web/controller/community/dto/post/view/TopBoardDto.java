package com.project.web.controller.community.dto.post.view;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TopBoardDto {
    // domain : board
    private final Integer id;
    private final String alias;
    private final List<TopPostDto> topPostDtos;

    @Builder
    public TopBoardDto(Integer id, String alias, List<TopPostDto> topPostDtos) {
        this.id = id;
        this.alias = alias;
        this.topPostDtos = topPostDtos;
    }
}
