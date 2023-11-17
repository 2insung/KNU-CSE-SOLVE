package com.project.web.controller.community.dto.post.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IncPostRecommendResponseDto {
    private final Boolean isSuccess;
    private final Integer recommendCount;

    @Builder
    public IncPostRecommendResponseDto(Boolean isSuccess, Integer recommendCount) {
        this.isSuccess = isSuccess;
        this.recommendCount = recommendCount;
    }
}
