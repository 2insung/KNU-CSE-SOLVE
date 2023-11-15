package com.project.web.controller.community.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostPageNumberDto {
    private String boardType;
    private Integer pageNumber;

    @Builder
    public PostPageNumberDto(String boardType, Integer pageNumber){
        this.boardType = boardType;
        this.pageNumber = pageNumber;
    }
}
