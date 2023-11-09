package com.project.web.controller.my.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyEditPageResponseDto {
    private Integer memberId;
    private String nickname;
    private String profileImage;
    private String description;
    private String grade;
    private String admissionYear;
    private String department;
    private Boolean isMine;

    @Builder
    private MyEditPageResponseDto(Integer memberId, String nickname, String profileImage, String description,
                                  String grade, String admissionYear, String department, Boolean isMine) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
        this.isMine = isMine;
    }
}
