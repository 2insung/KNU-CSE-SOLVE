package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TopHotPostDto {
    private Integer postId;
    private String boardType;
    private String title;

    @Builder
    public TopHotPostDto(Integer postId, String boardType, String title){
        this.postId = postId;
        this.boardType = boardType;
        this.title = title;
    }
}
