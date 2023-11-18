package com.project.web.controller.community.dto.post;

import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostPreviewDto {
    private final Integer id;
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
    public PostPreviewDto(Integer id, Integer authorId, Boolean isNotice, Boolean isHot, LocalDateTime createdAt,
                          String authorNickname, String authorProfileImage, String title, String summary, String thumbnail,
                          Integer hitCount, Integer recommendCount, Integer commentCount) {
        this.id = id;
        this.authorId = authorId;
        this.isNotice = isNotice;
        this.isHot = isHot;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.authorNickname = authorNickname.length() >= 6 ? authorNickname.substring(0,6) + ".." : authorNickname;
        this.authorProfileImage = authorProfileImage;
        this.title = title.length() > 30 ? title.substring(0, 30) + ".." : title;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.hitCount = hitCount;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
    }
}
