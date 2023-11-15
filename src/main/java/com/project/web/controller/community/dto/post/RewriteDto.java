package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RewriteDto {
    private Integer postId;
    private Integer postAuthorId;
    private String title;
    private String body;
    private Boolean isNotice;

    @Builder
    public RewriteDto(Integer postId, Integer postAuthorId, String title, String body, Boolean isNotice) {
        this.postId = postId;
        this.postAuthorId = postAuthorId;
        this.title = title;
        this.body = body;
        this.isNotice = isNotice;
    }
}
