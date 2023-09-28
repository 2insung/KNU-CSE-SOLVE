package com.project.web.controller.dto.my;

import com.project.web.domain.Member;
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
public class MyEditPageResponseDto {
    private String nickname;
    private String profileImage;
    private String description;
    private Integer grade;
    private String admissionYear;
    private String department;

    @Builder
    private MyEditPageResponseDto(Member member, MemberProfile memberProfile, MemberDetail memberDetail) {
        this.nickname = memberProfile.getNickname();
        this.profileImage = memberProfile.getProfileImage();
        this.description = memberDetail.getDescription();
        this.grade = memberDetail.getGrade();
        this.admissionYear = memberDetail.getAdmissionYear();
        this.department = memberDetail.getDepartment();
    }
}
