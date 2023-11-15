package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPostPageNumberDto {
    private Integer userId;
    private Integer pageNumber;

    @Builder
    public MyPostPageNumberDto(Integer userId, Integer pageNumber) {
        this.userId = userId;
        this.pageNumber = pageNumber;
    }
}
