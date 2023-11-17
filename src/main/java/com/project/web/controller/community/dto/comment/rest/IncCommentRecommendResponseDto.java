package com.project.web.controller.community.dto.comment.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IncCommentRecommendResponseDto {
    private final Boolean isSuccess;
    private final Integer recommendCount;

    @Builder
    public IncCommentRecommendResponseDto(Boolean isSuccess, Integer recommendCount) {
        this.isSuccess = isSuccess;
        this.recommendCount = recommendCount;
    }
}
