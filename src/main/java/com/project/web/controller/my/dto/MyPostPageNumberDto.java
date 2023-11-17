package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPostPageNumberDto {
    private final Integer memberId;
    private final Integer pageNumber;

    @Builder
    public MyPostPageNumberDto(Integer memberId, Integer pageNumber) {
        this.memberId = memberId;
        this.pageNumber = pageNumber;
    }
}
