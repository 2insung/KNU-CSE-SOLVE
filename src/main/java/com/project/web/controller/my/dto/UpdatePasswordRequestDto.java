package com.project.web.controller.my.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordRequestDto {
    private Integer userId;
    private String currentPassword;
    private String changePassword;
}
