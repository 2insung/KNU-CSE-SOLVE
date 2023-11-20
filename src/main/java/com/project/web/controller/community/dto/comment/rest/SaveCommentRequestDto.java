package com.project.web.controller.community.dto.comment.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SaveCommentRequestDto {
    @NotNull(message = "게시글 아이디를 입력해주세요.")
    private Integer postId;

    private Integer parentCommentId;

    @NotNull(message = "댓글 본문을 입력해주세요.")
    @Size(max = 500, message = "댓글은 500자 내로 입력해주세요.")
    private String commentBody;

    private Integer currentPageNumber;
}
