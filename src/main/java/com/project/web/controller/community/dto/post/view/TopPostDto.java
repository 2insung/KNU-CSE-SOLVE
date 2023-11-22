package com.project.web.controller.community.dto.post.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TopPostDto {
    // domain : Post
    private final Integer id;
    private final String createdAt;
    // domain : PostContent
    private final String title;
    // domain : Board
    private final String boardType;

    @Builder
    public TopPostDto(Integer id, LocalDateTime createdAt, String title, String boardType) {
        this.id = id;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.title = title;
        this.boardType = boardType;
    }
}