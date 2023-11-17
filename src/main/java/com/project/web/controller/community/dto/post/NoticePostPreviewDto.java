package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticePostPreviewDto {
    private final Integer id;
    private final String boardType;
    private final String title;

    @Builder
    public NoticePostPreviewDto(Integer id, String boardType, String title) {
        this.id = id;
        this.boardType = boardType;
        this.title = title;
    }
}
