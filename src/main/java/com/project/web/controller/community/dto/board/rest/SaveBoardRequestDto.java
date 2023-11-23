package com.project.web.controller.community.dto.board.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SaveBoardRequestDto {
    @NotBlank(message = "게시판 이름을 입력해주세요.")
    @Size(min = 4, max = 20, message = "게시판 이름은 4~20자 내로 입력해주세요.")
    private String boardAlias;

    @NotBlank(message = "게시판 설명을 입력해주세요.")
    @Size(max = 100, message = "게시판 설명은 100자 내로 입력해주세요.")
    private String boardDescription;

    @NotBlank(message = "게시판 카테고리를 입력해주세요.")
    private String boardCategory;
}
