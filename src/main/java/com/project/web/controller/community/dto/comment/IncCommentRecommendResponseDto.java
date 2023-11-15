package com.project.web.controller.community.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IncCommentRecommendResponseDto {
    private Boolean isSuccess;
    private Integer recommendCount;

    @Builder
    public IncCommentRecommendResponseDto(Boolean isSuccess, Integer recommendCount) {
        this.isSuccess = isSuccess;
        this.recommendCount = recommendCount;
    }
}
