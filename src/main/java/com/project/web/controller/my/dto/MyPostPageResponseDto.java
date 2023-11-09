package com.project.web.controller.my.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyPostPageResponseDto {
    private Integer memberId;
    private Boolean isMine;
}
