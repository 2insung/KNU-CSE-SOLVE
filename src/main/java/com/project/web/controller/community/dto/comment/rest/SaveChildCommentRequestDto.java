package com.project.web.controller.community.dto.comment.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SaveChildCommentRequestDto {
    @NotNull(message = "게시글 아이디를 입력해주세요.")
    private Integer postId;

    @NotNull(message = "대댓글을 달고자 하는 댓글 아이디를 입력해주세요.")
    private Integer parentCommentId;

    @NotNull(message = "댓글 본문을 입력해주세요.")
    @Size(max = 500, message = "댓글은 500자 내로 입력해주세요.")
    private String commentBody;

    @NotNull(message = "현재 페이지 번호를 입력해주세요.")
    private Integer currentPageNumber;
}