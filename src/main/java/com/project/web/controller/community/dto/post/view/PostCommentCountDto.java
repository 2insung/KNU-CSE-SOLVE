package com.project.web.controller.community.dto.post.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCommentCountDto {
    // Entity : PostStat
    private final Integer commentCount;
    private final Integer totalCommentCount;

    @Builder
    public PostCommentCountDto(Integer commentCount, Integer totalCommentCount) {
        this.commentCount = commentCount;
        this.totalCommentCount = totalCommentCount;
    }
}
