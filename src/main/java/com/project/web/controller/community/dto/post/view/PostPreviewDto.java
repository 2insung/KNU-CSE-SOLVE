package com.project.web.controller.community.dto.post.view;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostPreviewDto {
    // domain : Post
    private final Integer id;
    private final Integer authorId;
    private final Boolean isNotice;
    private final Boolean isHot;
    private final String createdAt;
    // domain : PostContent
    private final String title;
    private final String summary;
    private final String thumbnail;
    // domain : PostStat
    private final Integer hitCount;
    private final Integer recommendCount;
    private final Integer commentCount;
    // domain : MemberDetail
    private final String authorNickname;
    private final String authorProfileImage;

    @Builder
    public PostPreviewDto(Integer id, Integer authorId, Boolean isNotice, Boolean isHot, LocalDateTime createdAt,
                          String title, String summary, String thumbnail, Integer hitCount, Integer recommendCount,
                          Integer commentCount, String authorNickname, String authorProfileImage) {
        this.id = id;
        this.authorId = authorId;
        this.isNotice = isNotice;
        this.isHot = isHot;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.title = title;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.hitCount = hitCount;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
        this.authorNickname = authorNickname;
        this.authorProfileImage = authorProfileImage;
    }
}
