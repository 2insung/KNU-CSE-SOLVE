package com.insung.knucsesolve.controller.my.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class DeleteMyPostRequestDto {
    @NotNull(message = "게시판 아이디를 입력해주세요.")
    private Integer boardId;

    @NotNull(message = "게시글 아이디를 입력해주세요.")
    private Integer postId;

    @NotNull(message = "게시글 작성자 아이디를 입력해주세요.")
    private Integer postAuthorId;

    @NotNull(message = "현재 페이지 번호를 입력해주세요.")
    private Integer currentPageNumber;
}
