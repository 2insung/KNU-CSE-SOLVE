package com.project.web.controller.my.dto;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyScrapDto {
    private final Integer id;
    private final Integer memberId;
    private final String createdAt;
    private final String boardType;
    private final String boardAlias;
    private final String title;
    private final Boolean isMine;

    @Builder
    public MyScrapDto(Integer id, Integer memberId, LocalDateTime createdAt, String boardType,
                     String boardAlias, String title, Boolean isMine) {
        this.id = id;
        this.memberId = memberId;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.boardType = boardType;
        this.boardAlias = boardAlias;
        this.title = title;
        this.isMine = isMine;
    }
}
