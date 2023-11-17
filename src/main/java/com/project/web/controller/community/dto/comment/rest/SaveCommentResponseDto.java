package com.project.web.controller.community.dto.comment.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SaveCommentResponseDto {
    private final Integer pageNumber;

    @Builder
    public SaveCommentResponseDto(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
