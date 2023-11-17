package com.project.web.controller.my.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMyRequestDto {
    private Integer memberId;
    private String nickname;
    private MultipartFile file;
    private String description;
    private String grade;
    private String admissionYear;
    private String department;
}
