package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteMyCommentResponseDto {
    private Integer userId;
    private Integer currentPage;

    @Builder
    public DeleteMyCommentResponseDto(Integer userId, Integer currentPage) {
        this.userId = userId;
        this.currentPage = currentPage;
    }
}
