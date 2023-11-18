package com.project.web.controller.community.dto.post.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePostRequestDto {
    @NotBlank(message = "게시판 타입을 입력해주세요.")
    private String boardType;

    @NotNull(message = "게시글 아이디를 입력해주세요.")
    private Integer postId;

    @NotNull(message = "게시글 작성자 아이디를 입력해주세요.")
    private Integer postAuthorId;

    @NotBlank(message = "게시글 제목을 입력해주세요.")
    @Size(max = 40, message = "게시글 제목은 40자 내로 입력해주세요.")
    private String postTitle;

    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private String postBody;

    @NotNull(message = "게시글이 공지인지 입력해주세요.")
    private Boolean postIsNotice;
}
