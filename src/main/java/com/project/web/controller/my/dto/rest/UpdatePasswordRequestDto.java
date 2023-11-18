package com.project.web.controller.my.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordRequestDto {
    @NotNull(message = "멤버 아이디를 입력해주세요.")
    private Integer memberId;

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    @Size(min = 6, max = 20, message = "패스워드는 6~20자 내로 입력해주세요.")
    private String currentPassword;

    @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
    @Size(min = 6, max = 20, message = "변경할 패스워드는 6~20자 내로 입력해주세요.")
    private String changePassword;
}
