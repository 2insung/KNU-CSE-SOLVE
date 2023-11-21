package com.project.web.controller.my.dto.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteMyScrapResponseDto {
    private final Integer memberId;
    private final Integer pageNumber;

    @Builder
    public DeleteMyScrapResponseDto(Integer memberId, Integer pageNumber) {
        this.memberId = memberId;
        this.pageNumber = pageNumber;
    }
}
