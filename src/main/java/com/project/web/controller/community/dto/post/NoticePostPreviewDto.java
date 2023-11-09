package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticePostPreviewDto {
    private final Integer postId;
    private final String title;

    @Builder
    public NoticePostPreviewDto(Integer postId, String title) {
        this.postId = postId;
        this.title = title;
    }
}
