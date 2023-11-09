package com.project.web.controller.my.dto;


import com.project.web.domain.*;
import com.project.web.util.TimeFormattingUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MyPageResponseDto {
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
    private Integer postCount;
    private Integer commentCount;
    private Boolean isMine;

    @Builder
    private MyPageResponseDto(Boolean isDeleted, String username, String level, String nickname,
                              String profileImage, String description, String grade, String admissionYear,
                              String department, LocalDateTime createdAt, Integer postCount, Integer commentCount,
                              Boolean isMine) {
        this.isDeleted = isDeleted;
        this.username = username;
        this.level = level;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.grade = grade;
        this.admissionYear = admissionYear;
        this.department = department;
        this.createdAt = TimeFormattingUtil.localDateTimeFormatting(createdAt);
        this.postCount = postCount;
        this.commentCount = commentCount;
        this.isMine = isMine;
    }
}
