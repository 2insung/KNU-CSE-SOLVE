package com.project.web.controller.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckCodeRequestDto {
    private String username;
    private String code;
}
