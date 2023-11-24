package com.insung.knucsesolve.controller.community.dto.comment.view;

import com.insung.knucsesolve.util.TimeFormattingUtil;
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
    private final Integer boardId;
    private final String boardAlias;
    // Entity : PostContent
    private final String postTitle;

    @Builder
    public TopCommentDto(Integer postId, String body, LocalDateTime createdAt, Boolean isDeleted, Integer boardId,
                         String boardAlias, String postTitle) {
        this.postId = postId;
        this.body = body;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.isDeleted = isDeleted;
        this.boardId = boardId;
        this.boardAlias = boardAlias;
        this.postTitle = postTitle;
    }
}
