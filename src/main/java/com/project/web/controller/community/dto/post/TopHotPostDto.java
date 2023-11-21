package com.project.web.controller.community.dto.post;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TopHotPostDto {
    private final Integer id;
    private final String title;
    private final String createdAt;
    private final String boardType;
    private final Integer recommendCount;
    private final Integer commentCount;

    @Builder
    public TopHotPostDto(Integer id, String title, LocalDateTime createdAt, String boardType, Integer recommendCount,
                         Integer commentCount) {
        this.id = id;
        this.boardType = boardType;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.title = title;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
    }
}
