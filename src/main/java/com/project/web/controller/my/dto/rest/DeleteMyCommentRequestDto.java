package com.project.web.controller.my.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class DeleteMyCommentRequestDto {
    @NotNull(message = "게시글 아이디를 입력해주세요.")
    private Integer postId;

    @NotNull(message = "댓글 아이디를 입력해주세요.")
    private Integer commentId;

    @NotNull(message = "댓글 사용자 아이디를 입력해주세요.")
    private Integer commentAuthorId;

    @NotNull(message = "현재 페이지 번호를 입력해주세요.")
    private Integer currentPageNumber;
}
