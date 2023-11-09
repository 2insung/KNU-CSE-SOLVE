package com.project.web.controller.community.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteCommentResponseDto {
    private Integer commentPageNumber;

    @Builder
    public DeleteCommentResponseDto(Integer commentPageNumber) {
        this.commentPageNumber = commentPageNumber;
    }
}
