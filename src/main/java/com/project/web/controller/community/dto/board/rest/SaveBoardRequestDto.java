package com.project.web.controller.community.dto.board.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveBoardRequestDto {
    private String boardType;
    private String boardAlias;
    private String boardDescription;
    private String boardCategory;
}
