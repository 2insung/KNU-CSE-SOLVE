package com.project.web.controller.community.dto.post;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TopBoardDto {
    private final String boardAlias;
    private final String boardType;
    private final List<TopPostDto> topPostDtoList;

    @Builder
    public TopBoardDto(String boardAlias, String boardType, List<TopPostDto> topPostDtoList){
        this.boardAlias = boardAlias;
        this.boardType = boardType;
        this.topPostDtoList = topPostDtoList;
    }
}
