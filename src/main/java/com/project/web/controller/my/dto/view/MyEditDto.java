package com.project.web.controller.my.dto.view;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyEditDto {
    // domain : MemberDetail
    private final String nickname;
    private final String profileImage;
    private final String description;
    private final String grade;
    private final String admissionYear;
    private final String department;

    @Builder
    private MyEditDto(String nickname, String profileImage, String description, String grade, String admissionYear,
                      String department) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
    }
}
