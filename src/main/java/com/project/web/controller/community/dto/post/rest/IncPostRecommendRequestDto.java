package com.project.web.controller.community.dto.post.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class IncPostRecommendRequestDto {
    @NotNull(message = "게시글 아이디를 입력해주세요.")
    private Integer postId;
}
