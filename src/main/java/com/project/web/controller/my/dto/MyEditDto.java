package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyEditDto {
    private final Integer memberId;
    private final String nickname;
    private final String profileImage;
    private final String description;
    private final String grade;
    private final String admissionYear;
    private final String department;

    @Builder
    private MyEditDto(Integer memberId, String nickname, String profileImage, String description,
                      String grade, String admissionYear, String department) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
    }
}
