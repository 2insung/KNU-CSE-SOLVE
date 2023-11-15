package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyEditDto {
    private Integer userId;
    private String nickname;
    private String profileImage;
    private String description;
    private String grade;
    private String admissionYear;
    private String department;

    @Builder
    private MyEditDto(Integer userId, String nickname, String profileImage, String description,
                      String grade, String admissionYear, String department) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
    }
}
