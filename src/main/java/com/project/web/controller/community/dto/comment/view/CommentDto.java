package com.project.web.controller.community.dto.comment.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {
    // Entity : Comment
    private final Integer id;
    private final Integer authorId;
    private final Integer postId;
    private final Integer parentAuthorId;
    private final Boolean isPostAuthor;
    private final Boolean isRoot;
    private final Boolean isRootChild;
    private final Boolean isDeleted;
    private final String body;
    private final String createdAt;
    private final Integer recommendCount;
    // Entity : MemberDetail(댓글 작성자)
    private final String authorNickname;
    private final String authorProfileImage;
    // Entity : MemberDetail(대댓글의 달 경우 원댓글의 작성자)
    private final String parentAuthorNickname;
    // 현재 사용자가 작성한 Comment인지.
    private final Boolean isMine;

    @Builder
    public CommentDto(Integer id, Integer authorId, Integer postId, Integer parentAuthorId, Boolean isPostAuthor,
                      Boolean isRoot, Boolean isRootChild, Boolean isDeleted, String body, LocalDateTime createdAt,
                      Integer recommendCount, String authorNickname, String authorProfileImage, String parentAuthorNickname, Boolean isMine) {
        this.id = id;
        this.authorId = authorId;
        this.postId = postId;
        this.parentAuthorId = parentAuthorId;
        this.isPostAuthor = isPostAuthor;
        this.isRoot = isRoot;
        this.isRootChild = isRootChild;
        this.isDeleted = isDeleted;
        this.body = body;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
        this.recommendCount = recommendCount;
        this.authorNickname = authorNickname;
        this.authorProfileImage = authorProfileImage;
        this.parentAuthorNickname = parentAuthorNickname;
        this.isMine = isMine;
    }
}
