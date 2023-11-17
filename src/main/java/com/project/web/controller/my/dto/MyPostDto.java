package com.project.web.controller.my.dto;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPostDto {
    private final Integer id;
    private final Integer authorId;
    private final Integer boardId;
    private final String boardType;
    private final String boardAlias;
    private final String title;
    private final String createdAt;
    private final Boolean isMine;

    @Builder
    public MyPostDto(Integer id, Integer authorId, Integer boardId, String boardType, String boardAlias,
                     String title, LocalDateTime createdAt, Boolean isMine) {
        this.id = id;
        this.authorId = authorId;
        this.boardId = boardId;
        this.boardType = boardType;
        this.boardAlias = boardAlias;
        this.title = title;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.isMine = isMine;
    }
}
