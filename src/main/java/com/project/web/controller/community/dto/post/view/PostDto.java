package com.project.web.controller.community.dto.post.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    // domain : Post
    private final Integer id;
    private final Integer authorId;
    private final Boolean isNotice;
    private final Boolean isHot;
    private final String createdAt;
    private final String category;
    // domain : PostContent
    private final String title;
    private final String body;
    private final String updatedAt;
    // domain : PostHitCount
    private final Integer hitCount;
    // domain : PostRecommendCount
    private final Integer recommendCount;
    // domain : PostCommentCount
    private final Integer commentCount;
    private final Integer totalCommentCount;
    // domain : PostScrapCount
    private final Integer scrapCount;
    // domain : Board
    private final String boardType;
    // domain : MemberDetail
    private final String authorNickname;
    private final String authorProfileImage;
    //현재 사용자가 작성한 Post인지.
    private final Boolean isMine;

    @Builder
    public PostDto(Integer id, Integer authorId, Boolean isNotice, Boolean isHot, LocalDateTime createdAt,
                   String category, String title, String body, LocalDateTime updatedAt, Integer hitCount,
                   Integer recommendCount, Integer commentCount, Integer totalCommentCount, Integer scrapCount, Boolean isMine,
                   String boardType, String authorNickname, String authorProfileImage) {
        this.id = id;
        this.authorId = authorId;
        this.isNotice = isNotice;
        this.isHot = isHot;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.category = category;
        this.title = title;
        this.body = body;
        this.updatedAt = TimeFormattingUtil.localDateTimeFormattingAll(updatedAt);
        this.hitCount = hitCount;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
        this.totalCommentCount = totalCommentCount;
        this.scrapCount = scrapCount;
        this.isMine = isMine;
        this.boardType = boardType;
        this.authorNickname = authorNickname;
        this.authorProfileImage = authorProfileImage;
    }
}
