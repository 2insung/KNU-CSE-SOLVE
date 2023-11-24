package com.insung.knucsesolve.controller.community.dto.post.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class DeletePostRequestDto {
    @NotNull(message = "게시판 아이디를 입력해주세요.")
    private Integer boardId;

    @NotNull(message = "게시글 아이디를 입력해주세요.")
    private Integer postId;

    @NotNull(message = "게시글 작성자 아이디를 입력해주세요.")
    private Integer postAuthorId;
}
