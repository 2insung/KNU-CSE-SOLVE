package com.project.web.controller.community.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveBoardRequestDto {
    private String alias;
    private String type;
    private String description;
    private String category;
}
