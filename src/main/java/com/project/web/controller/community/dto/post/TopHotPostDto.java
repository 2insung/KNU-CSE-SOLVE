package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TopHotPostDto {
    private final Integer id;
    private final String boardType;
    private final String title;

    @Builder
    public TopHotPostDto(Integer id, String boardType, String title) {
        this.id = id;
        this.boardType = boardType;
        this.title = title.length() > 20 ? title.substring(0,20) + ".." : title;
    }
}
