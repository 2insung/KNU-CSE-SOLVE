package com.insung.knucsesolve.controller.my.dto.view;


import com.insung.knucsesolve.util.TimeFormattingUtil;
import com.insung.knucsesolve.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyDto {
    // domain : Member
    private final Integer id;
    private final Boolean isDeleted;
    // domain : MemberAuth
    private final String username;
    private final String role;
    // domain : MemberDetail
    private final String nickname;
    private final String profileImage;
    private final String description;
    private final String grade;
    private final String admissionYear;
    private final String department;
    private final String createdAt;
    //현재 사용자와 일치하는 Member인지.
    private final Boolean isMine;

    @Builder
    private MyDto(Integer id, Boolean isDeleted, String username, Role role, String nickname, String profileImage,
                  String description, String grade, String admissionYear, String department, LocalDateTime createdAt,
                  Boolean isMine) {
        this.id = id;
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
        this.isMine = isMine;
    }
}
