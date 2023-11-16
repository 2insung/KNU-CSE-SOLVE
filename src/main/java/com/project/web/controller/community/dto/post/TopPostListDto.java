package com.project.web.controller.community.dto.post;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TopPostListDto {
    private String alias;
    private List<TopPostDto> topPostDtoList;

    @Builder
    public TopPostListDto(String alias, List<TopPostDto> topPostDtoList){
        this.alias = alias;
        this.topPostDtoList = topPostDtoList;
    }
}
