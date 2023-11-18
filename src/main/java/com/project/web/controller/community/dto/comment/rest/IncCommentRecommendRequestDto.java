package com.project.web.controller.community.dto.comment.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class IncCommentRecommendRequestDto {
    @NotNull(message = "댓글 아이디를 입력해주세요.")
    private Integer commentId;
}
