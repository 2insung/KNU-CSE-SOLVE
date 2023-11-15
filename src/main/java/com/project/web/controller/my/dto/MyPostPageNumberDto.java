package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPostPageNumberDto {
    private String nickname;
    private Integer pageNumber;

    @Builder
    public MyPostPageNumberDto(String nickname, Integer pageNumber){
        this.nickname = nickname;
        this.pageNumber = pageNumber;
    }
}
