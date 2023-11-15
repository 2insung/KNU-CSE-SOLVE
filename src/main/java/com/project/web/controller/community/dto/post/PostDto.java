package com.project.web.controller.community.dto.post;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private final Integer postId;
    private final Integer authorId;
    private final Boolean isNotice;
    private final Boolean isHot;
    private final String createdAt;
    private final String category;
    private final String boardType;
    private final String authorNickname;
    private final String authorProfileImage;
    private final String title;
    private final String body;
    private final String updatedAt;
    private final Integer hitCount;
    private final Integer recommendCount;
    private final Integer commentCount;
    private final Integer totalCommentCount;
    private final Boolean isMine;

    @Builder
    public PostDto(Integer postId, Integer authorId, Boolean isNotice, Boolean isHot,
                   LocalDateTime createdAt, String category, String boardType, String authorNickname,
                   String authorProfileImage, String title, String body, LocalDateTime updatedAt,
                   Integer hitCount, Integer recommendCount, Integer commentCount, Integer totalCommentCount,
                   Boolean isMine) {
        this.postId = postId;
        this.authorId = authorId;
        this.isNotice = isNotice;
        this.isHot = isHot;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.category = category;
        this.boardType = boardType;
        this.authorNickname = authorNickname;
        this.authorProfileImage = authorProfileImage;
        this.title = title;
        this.body = body;
        this.updatedAt = TimeFormattingUtil.localDateTimeFormattingAll(updatedAt);
        this.hitCount = hitCount;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
        this.totalCommentCount = totalCommentCount;
        this.isMine = isMine;
    }
}
