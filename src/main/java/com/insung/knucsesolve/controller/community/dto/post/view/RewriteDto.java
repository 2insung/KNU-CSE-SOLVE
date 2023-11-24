package com.insung.knucsesolve.controller.community.dto.post.view;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RewriteDto {
    // domain : Post
    private final Integer id;
    private final Integer authorId;
    private final Boolean isNotice;
    // domain : PostContent
    private final String title;
    private final String body;

    @Builder
    public RewriteDto(Integer id, Integer authorId, Boolean isNotice, String title, String body) {
        this.id = id;
        this.authorId = authorId;
        this.isNotice = isNotice;
        this.title = title;
        this.body = body;
    }
}
