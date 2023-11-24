package com.insung.knucsesolve.controller.auth.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    @NotBlank(message = "유저네임을 입력해주세요.")
    @Size(min = 5, max = 50, message = "유저네임은 5~50자 내로 입력해주세요.")
    private String username;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 6, max = 20, message = "패스워드는 6~20자 내로 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 20, message = "닉네임은 2~20자 내로 입력해주세요.")
    private String nickname;
}
