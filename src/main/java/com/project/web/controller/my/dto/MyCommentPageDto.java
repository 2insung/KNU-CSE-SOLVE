package com.project.web.controller.my.dto;

import lombok.Builder;

public class MyCommentPageDto {
    private String nickname;
    private Integer pageNumber;

    @Builder
    public MyCommentPageDto(String nickname, Integer pageNumber){
        this.nickname = nickname;
        this.pageNumber = pageNumber;
    }
}
