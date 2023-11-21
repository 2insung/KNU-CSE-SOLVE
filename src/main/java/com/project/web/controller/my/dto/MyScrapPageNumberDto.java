package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyScrapPageNumberDto {
    private final Integer memberId;
    private final Integer pageNumber;

    @Builder
    public MyScrapPageNumberDto(Integer memberId, Integer pageNumber) {
        this.memberId = memberId;
        this.pageNumber = pageNumber;
    }
}
