package com.project.web.controller.my.dto.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyCommentDto {
    // domain : Comment
    private final Integer id;
    private final Integer authorId;
    private final Integer postId;
    private final Boolean isDeleted;
    private final String body;
    private final String createdAt;
    // domain : board
    private final String boardAlias;
    // domain : PostContent
    private final String postTitle;
    //현재 사용자가 작성한 Post인지.
    private final Boolean isMine;

    @Builder
    public MyCommentDto(Integer id, Integer authorId, Integer postId, Boolean isDeleted, String boardAlias,
                        String postTitle, String body, LocalDateTime createdAt, Boolean isMine) {
        this.id = id;
        this.authorId = authorId;
        this.postId = postId;
        this.isDeleted = isDeleted;
        this.body = body.length() > 50 ? body.substring(0, 50) + "..." : body;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.boardAlias = boardAlias;
        this.postTitle = postTitle;
        this.isMine = isMine;
    }
}
