package com.project.web.controller.community.dto.comment;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {
    private final Integer commentId;
    private final Integer postId;
    private final Integer authorId;
    private final String authorNickname;
    private final String authorProfileImage;
    private final Integer parentAuthorId;
    private final String parentAuthorNickname;
    private final Boolean isPostAuthor;
    private final Boolean isRoot;
    private final Boolean isRootChild;
    private final Boolean isDeleted;
    private final Boolean isMine;
    private final String body;
    private final String createdAt;
    private final Integer recommendCount;

    @Builder
    public CommentDto(Integer commentId, Integer postId, Integer authorId, String authorNickname,
                      String authorProfileImage, Integer parentAuthorId, String parentAuthorNickname, Boolean isPostAuthor,
                      Boolean isRoot, Boolean isRootChild, Boolean isDeleted, Boolean isMine,
                      String body, LocalDateTime createdAt, Integer recommendCount) {
        this.commentId = commentId;
        this.postId = postId;
        this.authorId = authorId;
        this.authorNickname = authorNickname;
        this.authorProfileImage = authorProfileImage;
        this.parentAuthorId = parentAuthorId;
        this.parentAuthorNickname = parentAuthorNickname;
        this.isPostAuthor = isPostAuthor;
        this.isRoot = isRoot;
        this.isRootChild = isRootChild;
        this.isDeleted = isDeleted;
        this.isMine = isMine;
        this.body = body;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.recommendCount = recommendCount;
    }
}
