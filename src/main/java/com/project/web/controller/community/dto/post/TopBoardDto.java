package com.project.web.controller.community.dto.post;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TopBoardDto {
    private final String boardAlias;
    private final List<TopPostDto> topPostDtoList;

    @Builder
    public TopBoardDto(String boardAlias, List<TopPostDto> topPostDtoList){
        this.boardAlias = boardAlias;
        this.topPostDtoList = topPostDtoList;
    }
}
