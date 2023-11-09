package com.project.web.controller.community.dto.post;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCommentCountDto {
    private Integer commentCount;
    private Integer totalCommentCount;

    @Builder
    public PostCommentCountDto(Integer commentCount, Integer totalCommentCount) {
        this.commentCount = commentCount;
        this.totalCommentCount = totalCommentCount;
    }
}
