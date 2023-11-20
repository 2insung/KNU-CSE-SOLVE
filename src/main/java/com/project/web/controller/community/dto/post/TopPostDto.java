package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TopPostDto {
    private final Integer id;
    private final String title;
    private final String boardType;

    @Builder
    public TopPostDto(Integer id, String title, String boardType) {
        this.id = id;
        this.title = title;
        this.boardType = boardType;
    }
}
