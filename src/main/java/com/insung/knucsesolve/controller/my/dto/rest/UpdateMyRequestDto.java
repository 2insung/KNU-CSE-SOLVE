package com.insung.knucsesolve.controller.my.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMyRequestDto {
    @Size(min = 2, max = 20, message = "닉네임은 2~20자 내로 입력해주세요.")
    private String nickname;

    private MultipartFile file;

    @Size(max = 100, message = "자기소개는 100자 내로 입력해주세요.")
    private String description;

    private String grade;

    private String admissionYear;

    private String department;
}
