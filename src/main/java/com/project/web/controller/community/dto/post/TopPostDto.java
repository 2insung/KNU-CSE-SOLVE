package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TopPostDto {
    private Integer postId;
    private String title;
    private String boardType;
    private Integer boardId;

    @Builder
    public TopPostDto(Integer postId, String boardType, String title, Integer boardId) {
        this.postId = postId;
        this.boardType = boardType;
        this.title = title;
        this.boardId = boardId;
    }
}
