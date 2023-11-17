package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RewriteDto {
    private final Integer id;
    private final Integer authorId;
    private final String title;
    private final String body;
    private final Boolean isNotice;

    @Builder
    public RewriteDto(Integer id, Integer authorId, String title, String body, Boolean isNotice) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.body = body;
        this.isNotice = isNotice;
    }
}
