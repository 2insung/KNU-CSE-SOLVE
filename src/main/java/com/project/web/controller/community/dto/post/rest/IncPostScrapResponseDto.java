package com.project.web.controller.community.dto.post.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IncPostScrapResponseDto {
    private final Boolean isSuccess;
    private final Integer scrapCount;

    @Builder
    public IncPostScrapResponseDto(Boolean isSuccess, Integer scrapCount) {
        this.isSuccess = isSuccess;
        this.scrapCount = scrapCount;
    }
}
