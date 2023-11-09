package com.project.web.controller.community.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SaveCommentResponseDto {
    private Integer commentPageNumber;

    @Builder
    public SaveCommentResponseDto(Integer commentPageNumber){
        this.commentPageNumber = commentPageNumber;
    }
}
