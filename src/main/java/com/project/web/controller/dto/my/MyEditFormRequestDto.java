package com.project.web.controller.dto.my;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class MyEditFormRequestDto {
    private String nickname;
    private MultipartFile file;
    private String description;
    private Integer grade;
    private String admissionYear;
    private String department;

}
