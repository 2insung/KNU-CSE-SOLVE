package com.project.web.controller.dto.my;


import com.project.web.domain.Member;
import com.project.web.domain.MemberAuth;
import com.project.web.domain.MemberDetail;
import com.project.web.domain.MemberProfile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MyPageResponseDto {
    private String username;
    private String level;
    private String nickname;
    private String profileImage;
    private Integer postCount;
    private Integer commentCount;
    private String description;
    private Integer grade;
    private String admissionYear;
    private String department;
    private LocalDateTime createdAt;

    @Builder
    private MyPageResponseDto(Member member, MemberProfile memberProfile, MemberDetail memberDetail) {
        this.username = member.getUsername();
        this.level = member.getLevel().name();
        this.nickname = memberProfile.getNickname();
        this.profileImage = memberProfile.getProfileImage();
        this.postCount = memberProfile.getPostCount();
        this.commentCount = memberProfile.getCommentCount();
        this.description = memberDetail.getDescription();
        this.grade = memberDetail.getGrade();
        this.admissionYear = memberDetail.getAdmissionYear();
        this.department = memberDetail.getDepartment();
        this.createdAt = memberDetail.getCreatedAt();
    }


}
