package com.project.web.controller.my.dto;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyCommentDto {
    private Integer boardId;
    private String boardType;
    private String boardAlias;
    private Integer postId;
    private String title;
    private String body;
    private String createdAt;
    private Boolean isDeleted;

    @Builder
    public MyCommentDto(Integer boardId, String boardType, String boardAlias, Integer postId,
                        String title, String body, LocalDateTime createdAt, Boolean isDeleted){
        this.boardId = boardId;
        this.boardType = boardType;
        this.boardAlias = boardAlias;
        this.postId = postId;
        this.title = title;
        this.body = body.length() > 50 ? body.substring(0, 50) + "..." : body;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.isDeleted = isDeleted;
    }
}
