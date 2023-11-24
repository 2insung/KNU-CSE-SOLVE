package com.insung.knucsesolve.controller.community.dto.post.view;

import com.insung.knucsesolve.util.TimeFormattingUtil;
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

    @Builder
    public TopHotPostDto(Integer id, LocalDateTime createdAt, String title, Integer recommendCount, Integer commentCount) {
        this.id = id;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.title = title;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
    }
}
