package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IncPostRecommendResponseDto {
    private Boolean isSuccess;
    private Integer recommendCount;

    @Builder
    public IncPostRecommendResponseDto(Boolean isSuccess, Integer recommendCount) {
        this.isSuccess = isSuccess;
        this.recommendCount = recommendCount;
    }
}
