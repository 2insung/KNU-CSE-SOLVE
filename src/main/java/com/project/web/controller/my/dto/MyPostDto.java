package com.project.web.controller.my.dto;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPostDto {
    private Integer boardId;
    private String boardType;
    private String boardAlias;
    private Integer postId;
    private Integer postAuthorId;
    private String title;
    private String createdAt;

    @Builder
    public MyPostDto(Integer boardId, String boardType, String boardAlias, Integer postId,
                     Integer postAuthorId, String title, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.boardType = boardType;
        this.boardAlias = boardAlias;
        this.postId = postId;
        this.postAuthorId = postAuthorId;
        this.title = title;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
    }


}
