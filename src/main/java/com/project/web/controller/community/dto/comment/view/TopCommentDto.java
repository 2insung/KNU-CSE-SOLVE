package com.project.web.controller.community.dto.comment.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TopCommentDto {
    // Entity : Comment
    private final Integer postId;
    private final String body;
    private final String createdAt;
    private final Boolean isDeleted;
    // Entity : Board
    private final String boardType;
    private final String boardAlias;
    // Entity : PostContent
    private final String postTitle;

    @Builder
    public TopCommentDto(Integer postId, String body, LocalDateTime createdAt, Boolean isDeleted, String boardType,
                         String boardAlias, String postTitle) {
        this.postId = postId;
        this.body = body;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.isDeleted = isDeleted;
        this.boardType = boardType;
        this.boardAlias = boardAlias;
        this.postTitle = postTitle;
    }
}
