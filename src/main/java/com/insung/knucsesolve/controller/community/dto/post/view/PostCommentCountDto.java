package com.insung.knucsesolve.controller.community.dto.post.view;

import lombok.Builder;
import lombok.Getter;

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
