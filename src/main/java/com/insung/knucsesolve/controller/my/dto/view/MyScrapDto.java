package com.insung.knucsesolve.controller.my.dto.view;

import com.insung.knucsesolve.util.TimeFormattingUtil;
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
    private final String boardAlias;
    private final Boolean isMine;

    @Builder
    public MyScrapDto(Integer memberId, Integer postId, LocalDateTime createdAt, String title, String boardAlias,
                      Boolean isMine) {
        this.memberId = memberId;
        this.postId = postId;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.title = title;
        this.boardAlias = boardAlias;
        this.isMine = isMine;
    }
}
