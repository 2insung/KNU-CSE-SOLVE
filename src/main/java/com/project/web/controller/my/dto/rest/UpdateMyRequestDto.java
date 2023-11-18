package com.project.web.controller.my.dto.rest;

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
    @NotNull(message = "멤버 아이디를 입력해주세요.")
    private Integer memberId;

    @Size(min = 2, max = 20, message = "닉네임은 2~20자 내로 입력해주세요.")
    private String nickname;

    private MultipartFile file;

    private String description;

    private String grade;

    private String admissionYear;

    private String department;
}
