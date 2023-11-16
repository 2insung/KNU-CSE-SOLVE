package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteMyPostResponseDto {
    private Integer userId;
    private Integer currentPage;

    @Builder
    public DeleteMyPostResponseDto(Integer userId, Integer currentPage) {
        this.userId = userId;
        this.currentPage = currentPage;
    }
}
