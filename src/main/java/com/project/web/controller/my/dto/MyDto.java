package com.project.web.controller.my.dto;


import com.project.web.domain.member.Role;
import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class MyDto {
    private final Boolean isDeleted;
    private final String username;
    private final String role;
    private final String nickname;
    private final String profileImage;
    private final String description;
    private final String grade;
    private final String admissionYear;
    private final String department;
    private final String createdAt;

    @Builder
    private MyDto(Boolean isDeleted, String username, Role role, String nickname, String profileImage,
                  String description, String grade, String admissionYear, String department, LocalDateTime createdAt) {
        this.isDeleted = isDeleted;
        this.username = username;
        this.role = role.getName();
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
        this.createdAt = TimeFormattingUtil.localDateTimeFormattingAll(createdAt);
    }
}
