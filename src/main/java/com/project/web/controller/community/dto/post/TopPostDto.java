package com.project.web.controller.community.dto.post;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TopPostDto {
    private final Integer id;
    private final String title;
    private final String createdAt;
    private final String boardType;

    @Builder
    public TopPostDto(Integer id, String title, LocalDateTime createdAt, String boardType) {
        this.id = id;
        this.title = title;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.boardType = boardType;
    }
}
