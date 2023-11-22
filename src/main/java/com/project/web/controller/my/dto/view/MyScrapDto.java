package com.project.web.controller.my.dto.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyScrapDto {
    // domain : PostScrapMember
    private final Integer memberId;
    // domain : Post
    private final Integer postId;
    private final String createdAt;
    private final String title;
    // domain : board
    private final String boardType;
    private final String boardAlias;
    private final Boolean isMine;

    @Builder
    public MyScrapDto(Integer memberId, Integer postId, LocalDateTime createdAt, String title, String boardType,
                      String boardAlias, Boolean isMine) {
        this.memberId = memberId;
        this.postId = postId;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.title = title;
        this.boardType = boardType;
        this.boardAlias = boardAlias;
        this.isMine = isMine;
    }
}
