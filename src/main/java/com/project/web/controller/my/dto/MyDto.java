package com.project.web.controller.my.dto;


import com.project.web.domain.member.Level;
import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MyDto {
    private Boolean isDeleted;
    private String username;
    private String level;
    private String nickname;
    private String profileImage;
    private String description;
    private String grade;
    private String admissionYear;
    private String department;
    private String createdAt;

    @Builder
    private MyDto(Boolean isDeleted, String username, Level level, String nickname,
                  String profileImage, String description, String grade, String admissionYear,
                  String department, LocalDateTime createdAt) {
        this.isDeleted = isDeleted;
        this.username = username;
        this.level = level.getName();
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
    }
}
