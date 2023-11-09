package com.project.web.controller.community.dto.post;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostPreviewDto {
    private final Integer postId;
    private final Integer authorId;
    private final Boolean isNotice;
    private final Boolean isHot;
    private final String createdAt;
    private final String authorNickname;
    private final String authorProfileImage;
    private final String title;
    private final String summary;
    private final String thumbnail;
    private final Integer hitCount;
    private final Integer recommendCount;
    private final Integer commentCount;

    @Builder
    public PostPreviewDto(Integer postId, Integer authorId, Boolean isNotice, Boolean isHot,
                          LocalDateTime createdAt, String authorNickname, String authorProfileImage, String title,
                          String summary, String thumbnail, Integer hitCount, Integer recommendCount,
                          Integer commentCount) {
        this.postId = postId;
        this.authorId = authorId;
        this.isNotice = isNotice;
        this.isHot = isHot;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.authorNickname = authorNickname;
        this.authorProfileImage = authorProfileImage;
        this.title = title;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.hitCount = hitCount;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
    }
}
