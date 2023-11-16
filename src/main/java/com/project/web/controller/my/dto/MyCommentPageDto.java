package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyCommentPageDto {
    private Integer userId;
    private Integer pageNumber;

    @Builder
    public MyCommentPageDto(Integer userId, Integer pageNumber){
        this.userId = userId;
        this.pageNumber = pageNumber;
    }
}
