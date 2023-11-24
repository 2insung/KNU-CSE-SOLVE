package com.insung.knucsesolve.controller.community.dto.post.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePostResponseDto {
    private final String postId;

    @Builder
    public UpdatePostResponseDto(Integer postId) {
        this.postId = postId.toString();
    }
}
