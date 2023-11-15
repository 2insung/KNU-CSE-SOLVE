package com.project.web.controller.community.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentPageNumberDto {
    private Integer postId;
    private Integer pageNumber;

    @Builder
    public CommentPageNumberDto(Integer postId, Integer pageNumber){
        this.postId = postId;
        this.pageNumber = pageNumber;
    }
}
