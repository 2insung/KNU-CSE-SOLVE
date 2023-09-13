package com.project.web.controller.dto;

import com.project.web.domain.PostPreview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostPreviewResponseDto {
    private String summary;
    private String thumbnail;
    private String title;
    private LocalDateTime createdAt;
    private Boolean isNotification;
    private Boolean isHot;
    private Integer hits;
    private Integer recommend;
    private Integer commentCount;

    public PostPreviewResponseDto(PostPreview postPreview){
        this.summary = postPreview.getSummary();
        this.thumbnail = postPreview.getThumbnail();
        this.title = postPreview.getPost().getTitle();
        this.createdAt = postPreview.getPost().getCreatedAt();
        this.isNotification = postPreview.getPost().getIsNotification();
        this.isHot = postPreview.getPost().getIsHot();
        this.hits = postPreview.getPost().getHits();
        this.recommend = postPreview.getPost().getRecommend();
        this.commentCount = postPreview.getPost().getCommentCount();
    }
}
