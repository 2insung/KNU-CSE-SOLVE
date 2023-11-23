package com.project.web.controller.community.dto.post.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TopHotPostDto {
    // domain : Post
    private final Integer id;
    private final String createdAt;
    // domain : PostContent
    private final String title;
    // domain : PostStat
    private final Integer recommendCount;
    private final Integer commentCount;
    // domain : Board
    private final String boardType;

    @Builder
    public TopHotPostDto(Integer id, LocalDateTime createdAt, String title, Integer recommendCount, Integer commentCount,
                         String boardType) {
        this.id = id;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.title = title;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
        this.boardType = boardType;
    }
}
