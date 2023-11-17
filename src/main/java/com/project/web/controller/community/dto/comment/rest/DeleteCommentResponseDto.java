package com.project.web.controller.community.dto.comment.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteCommentResponseDto {
    private final Integer pageNumber;

    @Builder
    public DeleteCommentResponseDto(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
