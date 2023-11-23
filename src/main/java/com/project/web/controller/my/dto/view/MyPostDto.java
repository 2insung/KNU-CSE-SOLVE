package com.project.web.controller.my.dto.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPostDto {
    //domain : Post
    private final Integer id;
    private final Integer authorId;
    private final String createdAt;
    //domain : PostContent
    private final String title;
    //domain : Board
    private final Integer boardId;
    private final String boardAlias;
    //현재 사용자가 작성한 Post인지.
    private final Boolean isMine;

    @Builder
    public MyPostDto(Integer id, Integer authorId, LocalDateTime createdAt, String title, Integer boardId,
                     String boardAlias, Boolean isMine) {
        this.id = id;
        this.authorId = authorId;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.title = title;
        this.boardId = boardId;
        this.boardAlias = boardAlias;
        this.isMine = isMine;
    }
}
