package com.insung.knucsesolve.controller.community.dto.post.view;

import com.insung.knucsesolve.util.TimeFormattingUtil;
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

    @Builder
    public TopPostDto(Integer id, LocalDateTime createdAt, String title) {
        this.id = id;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.title = title;
    }
}