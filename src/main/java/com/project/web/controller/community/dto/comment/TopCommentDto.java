package com.project.web.controller.community.dto.comment;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TopCommentDto {
    private final Integer postId;
    private final String body;
    private final String createdAt;
    private final Boolean isDeleted;
    private final String boardType;
    private final String boardAlias;
    private final String boardTitle;

    @Builder
    public TopCommentDto(Integer postId, String body, LocalDateTime createdAt, Boolean isDeleted, String boardType,
                         String boardAlias, String boardTitle) {
        this.postId = postId;
        this.isDeleted = isDeleted;
        this.boardType = boardType;
        this.body = body.length() > 50 ? body.substring(0, 50) + "..." : body;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
    }
}
