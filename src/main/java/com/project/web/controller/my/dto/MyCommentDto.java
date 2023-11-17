package com.project.web.controller.my.dto;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyCommentDto {
    private final Integer id;
    private final Integer authorId;
    private final Integer postId;
    private final Boolean isDeleted;
    private final String body;
    private final String createdAt;
    private final Integer boardId;
    private final String boardType;
    private final String boardAlias;
    private final String title;
    private final Boolean isMine;

    @Builder
    public MyCommentDto(Integer id, Integer authorId, Integer postId, Boolean isDeleted, Integer boardId,
                        String boardType, String boardAlias, String title, String body, LocalDateTime createdAt,
                        Boolean isMine) {
        this.id = id;
        this.authorId = authorId;
        this.postId = postId;
        this.isDeleted = isDeleted;
        this.boardId = boardId;
        this.boardType = boardType;
        this.boardAlias = boardAlias;
        this.title = title;
        this.body = body.length() > 50 ? body.substring(0, 50) + "..." : body;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.isMine = isMine;
    }
}
