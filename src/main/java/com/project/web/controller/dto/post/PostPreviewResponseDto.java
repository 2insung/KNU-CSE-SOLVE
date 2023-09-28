package com.project.web.controller.dto.post;

import com.project.web.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class PostPreviewResponseDto {
    private Long id;
    private String type;
    private String author;
    private String summary;
    private String thumbnail;
    private String title;
    private String createdAt;
    private Boolean isNotification;
    private Boolean isHot;
    private Integer hitCount;
    private Integer recommendCount;
    private Integer totalCommentCount;

    @Builder
    public PostPreviewResponseDto(Post post, Board board, MemberProfile memberProfile, PostContent postContent, PostInfo postInfo) {
        this.id = post.getId();
        this.type = board.getType();
        this.author = memberProfile.getNickname();
        this.summary = postContent.getSummary();
        this.thumbnail = postContent.getThumbnail();
        this.title = postContent.getTitle();
        this.createdAt = localDateTimeFormatting(postContent.getCreatedAt());
        this.isNotification = postInfo.getIsNotification();
        this.isHot = postInfo.getIsHot();
        this.hitCount = postInfo.getHitCount();
        this.recommendCount = postInfo.getRecommendCount();
        this.totalCommentCount = postInfo.getTotalCommentCount();
    }

    private String localDateTimeFormatting(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();

        if (localDateTime.toLocalDate().isEqual(now.toLocalDate())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return localDateTime.format(formatter);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
            return localDateTime.format(formatter);
        }
    }

}
