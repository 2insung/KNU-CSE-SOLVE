package com.insung.knucsesolve.controller.community.dto.post.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SavePostRequestDto {
    @NotNull(message = "게시판 아이디를 입력해주세요.")
    private Integer boardId;

    @NotBlank(message = "게시글 제목을 입력해주세요.")
    @Size(max = 50, message = "게시글 제목은 50자 내로 입력해주세요.")
    private String postTitle;

    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private String postBody;

    @NotNull(message = "게시글이 공지인지 입력해주세요.")
    private Boolean postIsNotice;
}
